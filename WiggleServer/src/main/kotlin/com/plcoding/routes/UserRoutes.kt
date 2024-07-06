package com.plcoding.routes

import com.google.gson.Gson
import com.plcoding.data.requests.UpdateProfileReq
import com.plcoding.response.ApiResponse
import com.plcoding.response.ApiResponseMessages
import com.plcoding.response.UserResponseItem
import com.plcoding.service.UserService
import com.plcoding.util.Constants.banner_picture_path
import com.plcoding.util.Constants.base_url
import com.plcoding.util.Constants.profile_picture_path
import com.plcoding.util.QueryParams
import com.plcoding.util.save
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File


fun Route.searchuser(
    userService: UserService
){
    authenticate {
        get("api/user/search"){
            val query = call.parameters[QueryParams.param_query]
                if(query == null || query.isBlank()){
                call.respond(
                    HttpStatusCode.OK, listOf<UserResponseItem>()
                )
            return@get
            }
        val searchResults = userService.searchForUsers(query,call.userId)
        call.respond(
            HttpStatusCode.OK,searchResults
        )
        }
    }
}
fun Route.getUserProfile(userService: UserService){
    authenticate {
        get("/api/user/profile"){
            val userId = call.parameters[QueryParams.param_user_id]
            if(userId==null || userId.isBlank()){
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@get
            }
            val profileResponse = userService.getUserProfile(userId,call.userId)
            if(profileResponse == null){
                call.respond(
                    HttpStatusCode.OK,ApiResponse<Unit>(message=ApiResponseMessages.user_not_found,false)
                )
                return@get
            }
            call.respond(
                HttpStatusCode.OK,ApiResponse(
                    successful = true,
                    data = profileResponse
                )
            )
        }
    }
}
fun Route.updateUserProfile(userService: UserService){
    val gson : Gson by inject()
    authenticate {
        put("/api/user/update"){
                val multipart = call.receiveMultipart()
                var updateProfileReq: UpdateProfileReq? = null
                var profilePicFileName: String? = null
                var bannerPicFileName: String? = null
                multipart.forEachPart { partData ->
                    when(partData){
                        is PartData.FormItem -> {
                            if (partData.name == "update_profile_data") {
                                updateProfileReq = gson.fromJson(
                                    partData.value,
                                    UpdateProfileReq::class.java
                                )
                            }
                        }
                        is PartData.FileItem -> {
                            if(partData.name=="profile_picture"){
                                profilePicFileName= partData.save(profile_picture_path)
                            } else if(partData.name=="banner_image"){
                               bannerPicFileName= partData.save(banner_picture_path)

                            }

                        }
                        is PartData.BinaryItem -> Unit
                        is PartData.BinaryChannelItem -> Unit
                    }
                }
            val profilePictureUrl = "${base_url}profile_pictures/$profilePicFileName"
            val bannerPictureUrl = "${base_url}banner_pictures/$bannerPicFileName"
            updateProfileReq?.let { request ->
                val updateAcknowledged = userService.updateUser(
                    userId = call.userId,
                    profileImageUrl = if(profilePicFileName == null) null else { profilePictureUrl},
                    updateProfileReq=request,
                    bannerUrl =  if(bannerPicFileName == null) null else { bannerPictureUrl}
                )
                if (updateAcknowledged) {
                    call.respond(HttpStatusCode.OK,
                        ApiResponse<Unit>(successful = true))
                } else {
                    File("${profile_picture_path}$profilePicFileName").delete()
                    File("${banner_picture_path}$bannerPicFileName").delete()
                    call.respond(HttpStatusCode.InternalServerError,ApiResponse<Unit>(successful = false))
                }

            } ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            }
        }
    }
