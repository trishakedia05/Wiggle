package com.plcoding.routes

import com.plcoding.data.models.Activity
import com.plcoding.data.requests.AccReq
import com.plcoding.data.requests.FollowReq
import com.plcoding.data.util.ActivityType
import com.plcoding.repository.follow.FollowRepository
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.service.ActivityService
import com.plcoding.service.FollowService
import com.plcoding.util.QueryParams
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.followUser(followService: FollowService,
                     activityService: ActivityService
) {
    authenticate {
        post("/api/following/follow") {
            val request = call.receiveNullable<FollowReq>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val didUserExist=followService.followUserIfExists(request,call.userId)
            if(didUserExist){
                activityService.createActivity(
                    Activity(
                        timestamp = System.currentTimeMillis(),
                        byUserId = call.userId,
                        toUserId = request.followedUserId,
                        type = ActivityType.FollowedUser.type,
                        parentId = ""
                    )
                )
                call.respond( HttpStatusCode.OK,
                    ApiResponse<Unit>(successful = true))

            } else{
                call.respond( HttpStatusCode.OK,
                    ApiResponse<Unit>(successful = false,
                        message=ApiResponseMessages.user_not_found))
            }
        }
    }

}
fun Route.unfollowUser(followService: FollowService) {
    authenticate{
        delete("/api/following/unfollow") {
            val userId = call.parameters[QueryParams.param_user_id] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val didUserExist=followService.unfollowUserIfExists(userId,call.userId)
            print("USER ID ${call.userId}")
            if(didUserExist){
                call.respond( HttpStatusCode.OK,
                    ApiResponse<Unit>(successful = true))

            } else{
                call.respond( HttpStatusCode.OK,
                    ApiResponse<Unit>(successful = false,
                        message=ApiResponseMessages.user_not_found))
            }
        }
    }

}