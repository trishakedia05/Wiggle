package com.plcoding.util


import com.plcoding.plugins.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

val ApplicationCall.userId: String
        get() = principal<JWTPrincipal>()?.userId.toString()

//suspend fun PipelineContext<Unit,ApplicationCall>.ifEmailBelongsToUser(
//    userId: String,
//   validateEmail : suspend (email: String, userId: String) -> Boolean,
//    onSuccess: suspend() -> Unit
//){
//    val isEmailByUser = validateEmail(
//        call.principal<JWTPrincipal>()?.email ?: "",
//        userId
//    )
//    if (isEmailByUser){
//        onSuccess()
//    }else{
//        call.respond(HttpStatusCode.Unauthorized)
//    }
//
//}