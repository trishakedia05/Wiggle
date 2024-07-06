package com.example.wiggle.feature_post.data.remote

import com.example.wiggle.core.data.dto.response.ApiResponse
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_post.data.remote.dto.CommentDto
import com.example.wiggle.feature_post.data.remote.dto.UserItemDto
import com.example.wiggle.feature_post.data.remote.request.CreateCommentRequest
import com.example.wiggle.feature_post.data.remote.request.CreatePostReq
import com.example.wiggle.feature_post.data.remote.request.LikeUpdateRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostForFollows(
       @Query("page") page:Int,
        @Query("pageSize") pageSize: Int
    ) : List<PostM>

    @GET("/api/user/posts")
    suspend fun getPostsForProfile(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<PostM>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ) : ApiResponse<Unit>

    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId: String
    ): ApiResponse<PostM>

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): List<CommentDto>

    @POST("/api/comment/create")
    suspend fun createComment(
        @Body request: CreateCommentRequest
    ): ApiResponse<Unit>

    @POST("/api/like")
    suspend fun likeParent(
        @Body request: LikeUpdateRequest
    ): ApiResponse<Unit>

    @DELETE("/api/unlike")
    suspend fun unlikeParent(
        @Query("parentId") parentId: String,
        @Query("parentType") parentType: Int
    ): ApiResponse<Unit>

    @GET("/api/like/parent")
    suspend fun getLikesForParent(
        @Query("parentId") parentId: String
    ): List<UserItemDto>

    @DELETE("/api/post/delete")
    suspend fun deletePost(
        @Query("postId") postId: String,
    )

    companion object{
        const val BASE_URL = "http://192.168.221.1:8001"
    }
}