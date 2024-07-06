package com.plcoding.routes

import com.google.gson.Gson
import com.plcoding.data.requests.CreatePostReq
import com.plcoding.data.requests.DeletePostReq
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.service.CommentService
import com.plcoding.service.LikeService
import com.plcoding.service.PostService
import com.plcoding.service.UserService
import com.plcoding.util.Constants
import com.plcoding.util.QueryParams
import com.plcoding.util.save
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*


fun Route.createPost(postService: PostService)
{
    val gson by inject<Gson>()
authenticate {
    post("/api/post/create"){
        val multipart = call.receiveMultipart()
        var createPostReq: CreatePostReq? = null
        var fileName: String? = null
        multipart.forEachPart { partData ->
            when(partData){
                is PartData.FormItem -> {
                    if (partData.name == "post_data") {
                        createPostReq = gson.fromJson(
                            partData.value,
                            CreatePostReq::class.java
                        )
                    }
                }
                is PartData.FileItem -> {
                    fileName=partData.save(Constants.post_picture_path)


                }
                is PartData.BinaryItem -> Unit
                is PartData.BinaryChannelItem -> Unit
            }
        }
        val postPictureUrl = "${Constants.base_url}post_pictures/$fileName"
        createPostReq?.let { request ->
            val createPostAcknowledged = postService.createPost(
               userId =  call.userId, imageUrl = postPictureUrl,request=request
            )
            if (createPostAcknowledged) {
                call.respond(HttpStatusCode.OK,
                    ApiResponse<Unit>(successful = true))
            } else {
                File("${Constants.post_picture_path}$fileName").delete()
                call.respond(HttpStatusCode.InternalServerError,ApiResponse<Unit>(successful = false))
            }

        } ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
    }
}
}


fun Route.getPostForFollows(
    postService: PostService,
){
    authenticate {
        get("/api/post/get"){
            val page = call.parameters[QueryParams.param_page]?.toIntOrNull() ?:0
            val pageSize = call.parameters[QueryParams.param_page_size]?.toIntOrNull() ?: Constants.default_post_page_size

            val posts = postService.getPostForFollows(call.userId,page,pageSize)
            call.respond(HttpStatusCode.OK,posts)

        }
    }
}

fun Route.deletePost(
    postService: PostService,
    likeService: LikeService,
    commentService: CommentService
) {
    authenticate {
        delete("api/post/delete") {

            val request = call.receiveNullable<DeletePostReq>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val post = postService.getPost(request.postId)
            if (post == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            if(post.userId==call.userId){
                postService.deletePost(request.postId)
                likeService.deleteLikesForParent(request.postId)
                commentService.deleteCommentsForPost(request.postId)
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.Unauthorized)
            }

        }
    }
}
fun Route.getPostForProfile(
    postService: PostService,
){
    authenticate {
        get("/api/user/posts"){
            val userId = call.parameters[QueryParams.param_user_id]
            val page = call.parameters[QueryParams.param_page]?.toIntOrNull() ?:0
            val pageSize = call.parameters[QueryParams.param_page_size]?.toIntOrNull() ?: Constants.default_post_page_size

            val posts = postService.getPostsForProfile(
                 ownUserId = call.userId,
                userId= userId ?: call.userId,
               page =  page,
               pageSize =  pageSize)
            call.respond(HttpStatusCode.OK,posts)

        }
    }
}

fun Route.getPostDetails(postService: PostService) {
    authenticate{
        get("/api/post/details") {
            val postId = call.parameters["postId"] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val post = postService.getPostDetails(call.userId, postId) ?: kotlin.run {
                print("\n userid ${call.userId} \n")
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    successful = true,
                    data = post
                )
            )
        }
    }

}
















