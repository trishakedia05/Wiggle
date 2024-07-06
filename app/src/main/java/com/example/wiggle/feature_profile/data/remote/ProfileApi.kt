package com.example.wiggle.feature_profile.data.remote

import com.example.wiggle.core.data.dto.response.ApiResponse
import com.example.wiggle.feature_profile.data.remote.response.ProfileResponse
import com.example.wiggle.feature_post.data.remote.dto.UserItemDto
import com.example.wiggle.feature_profile.data.remote.request.FollowUpdateRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): ApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?,
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): ApiResponse<Unit>

    @GET("/api/user/search")
    suspend fun searchUser(
        @Query("query") query: String
    ) : List<UserItemDto>

    @POST("/api/following/follow")
    suspend fun followUser(
        @Body request: FollowUpdateRequest
    ): ApiResponse<Unit>

    @DELETE("/api/following/unfollow")
    suspend fun unfollowUser(
        @Query("userId") userId: String
    ): ApiResponse<Unit>

    companion object{
        const val BASE_URL = "http://192.168.221.1:8001"
    }
}
