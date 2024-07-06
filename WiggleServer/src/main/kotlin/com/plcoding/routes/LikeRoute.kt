package com.plcoding.routes

import com.plcoding.data.requests.LikeUpdateReq
import com.plcoding.data.util.ParentType
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.service.ActivityService
import com.plcoding.service.LikeService
import com.plcoding.util.QueryParams
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.likeRoute(
    likeService: LikeService,
    activityService: ActivityService
){
    authenticate {
        post("/api/like"){
            val request = call.receiveNullable<LikeUpdateReq>() ?: kotlin.run{
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            print(call.userId)
            val likeSuccessful =likeService.likeParent(call.userId,request.parentId,request.parentType)
                if(likeSuccessful){
                   activityService.addLikeActivity(
                       byUserId = userId,
                       parentId = request.parentId,
                       parentType = ParentType.fromType(request.parentType)
                   )
                    call.respond(HttpStatusCode.OK,
                        ApiResponse<Unit>(successful = true))

                }else{
                    call.respond(HttpStatusCode.OK,
                        ApiResponse<Unit>(message=ApiResponseMessages.user_not_found,false))
                }

        }
    }
}
fun Route.unlikeRoute(
    likeService: LikeService,
){
    authenticate {
        delete("/api/unlike"){
            val parentId = call.parameters[QueryParams.PARAM_PARENT_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val parentType = call.parameters[QueryParams.PARAM_PARENT_TYPE]?.toIntOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val unlikeSuccessful = likeService.unlikeParent(call.userId, parentId, parentType)
                if(unlikeSuccessful){
                    call.respond(HttpStatusCode.OK,
                        ApiResponse<Unit>(successful = true))

                }else{
                    call.respond(HttpStatusCode.OK,
                        ApiResponse<Unit>(message = ApiResponseMessages.user_not_found,false))
                }
            }

    }
}
fun Route.getLikesForParent(
    likeService: LikeService
){
    authenticate {
        get("/api/like/parent") {
            val parentId = call.parameters[QueryParams.param_parent_id] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val userWhoLikedParent = likeService.getUsersWhoLikedParent(
                parentId,call.userId
            )
            call.respond(HttpStatusCode.OK,userWhoLikedParent)

        }
    }
}