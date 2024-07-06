package com.plcoding.repository.post

import com.plcoding.data.models.Post
import com.plcoding.response.PostResponse
import com.plcoding.util.Constants

interface PostRepository {
    suspend fun createPost(post: Post) :Boolean
    suspend fun deletePost(postId: String)
    suspend fun getPostByFollows(ownUserId: String,
                                 page: Int,
                                 pageSize: Int = Constants.default_post_page_size) : List<PostResponse>
    suspend fun getPostsForProfile(ownUserId: String,
                                   userId: String,
                                 page: Int,
                                 pageSize: Int = Constants.default_post_page_size) : List<PostResponse>
    suspend fun getPost(postId: String) : Post?
    suspend fun getPostDetails(userId: String, postId: String): PostResponse?

}