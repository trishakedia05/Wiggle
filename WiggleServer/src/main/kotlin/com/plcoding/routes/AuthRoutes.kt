package com.plcoding.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.plcoding.data.requests.AccReq
import com.plcoding.data.requests.LoginReq
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.response.AuthResponse
import com.plcoding.routes.authenticate
import com.plcoding.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.createUserRoutes(userService: UserService) {
    post("/api/user/create"){

        val request = call.receiveNullable<AccReq>() ?: kotlin.run{
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        if(userService.doesUserWithEmailExist(request.email)){
            call.respond(
                ApiResponse<Unit>(message = ApiResponseMessages.user_already_exists,false)
            )
            return@post
        }
        when(userService.validateCreateAccountReq(request)){
            is UserService.ValidationEvent.ErrorFieldEmpty ->{
                call.respond(
                    ApiResponse<Unit>(message = ApiResponseMessages.field_blank,successful = false)
                )
                return@post
            }
            is UserService.ValidationEvent.Success -> {
                userService.createUser(request)
                call.respond(
                    ApiResponse<Unit>(successful = true)
                )
            }
        }

    }
}

fun Route.loginUser(userService: UserService,
                    jwtIssuer: String,
                    jwtAudience:String,
                    jwtSecret: String){
    post("api/user/login"){

        val request = call.receiveNullable<LoginReq>() ?: kotlin.run{
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        if(request.email.isBlank() || request.password.isBlank()){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val user = userService.getUserByEmail(request.email) ?: kotlin.run {
            call.respond(
                HttpStatusCode.OK,
                ApiResponse<Unit>(successful = false,
                    message = ApiResponseMessages.invalid_email)
            )
            return@post
        }
        val isCorrectPass =userService.isValidPassword(request.password,user.password)

        if(isCorrectPass){
            val expiresIn = 1000L *60L *60L *24L *365L
            val token = JWT.create()
                .withClaim("userId",user.id)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(successful = true,
                    data= AuthResponse(token=token, userId = user.id)
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                ApiResponse<Unit>(successful = false,
                    message = ApiResponseMessages.invalid_cred)
            )

        }



    }
}

fun Route.authenticate(){
    authenticate {
        get("api/user/authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }

}