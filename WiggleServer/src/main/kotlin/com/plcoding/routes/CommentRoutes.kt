package com.plcoding.routes

import com.plcoding.data.models.Activity
import com.plcoding.data.requests.CreateCommentReq
import com.plcoding.data.requests.DeleteCommentReq
import com.plcoding.data.requests.DeletePostReq
import com.plcoding.data.requests.LikeUpdateReq
import com.plcoding.data.util.ParentType
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.service.ActivityService
import com.plcoding.service.CommentService
import com.plcoding.service.LikeService
import com.plcoding.service.UserService
import com.plcoding.util.QueryParams
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commentRoute(
   commentService: CommentService,
   activityService: ActivityService
) {
    authenticate {
        post("/api/comment/create") {
            val request = call.receiveNullable<CreateCommentReq>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId

            when(commentService.createComment(request,call.userId)){
                is CommentService.ValidationEvent.ErrorFieldEmpty -> {
                    call.respond(HttpStatusCode.OK,ApiResponse<Unit>(message = ApiResponseMessages.field_blank,false))
                }
                is CommentService.ValidationEvent.ErrorCommentTooLong -> {
                    call.respond(HttpStatusCode.OK,ApiResponse<Unit>(message = ApiResponseMessages.comment_too_long,false))
                }
                is CommentService.ValidationEvent.Success -> {
                    activityService.addCommentActivity(
                            byUserId = userId,
                            postId = request.postId,
                        )
                    call.respond(HttpStatusCode.OK,ApiResponse<Unit>(successful = true))
                }
                is CommentService.ValidationEvent.UserNotFound -> {
                    call.respond(
                        HttpStatusCode.OK,
                        ApiResponse<Unit>(
                            successful = false,
                            message = "User not found"
                        )
                    )
                }
            }
        }
    }
}
fun Route.getCommentsForPost(
    commentService: CommentService,
){
    authenticate {
        get("/api/comment/get") {
            val postId = call.parameters[QueryParams.param_postId] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val comments = commentService.getCommentsForPost(postId,call.userId)
            call.respond(HttpStatusCode.OK,comments)
        }
    }
}
fun Route.deleteComment(
    commentService: CommentService,
    likeService: LikeService
){
    authenticate {
        delete("/api/comment/delete") {
            val request = call.receiveNullable<DeleteCommentReq>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val comment = commentService.getCommentById(request.commentId)
            if(comment?.userId !=call.userId){
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            val deleted =  commentService.deleteComment(request.commentId)
            if(deleted){
                likeService.deleteLikesForParent(request.commentId)
                call.respond(HttpStatusCode.OK,ApiResponse<Unit>(successful = true))
            }else{
                call.respond(HttpStatusCode.NotFound,ApiResponse<Unit>(successful = false)) }
        }
    }
}
