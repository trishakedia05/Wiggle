package com.plcoding.service

import com.plcoding.data.models.Post
import com.plcoding.data.requests.CreatePostReq
import com.plcoding.repository.post.PostRepository
import com.plcoding.response.PostResponse
import com.plcoding.util.Constants

class PostService (private val postRepository: PostRepository) {
    suspend fun createPost(request: CreatePostReq, userId: String, imageUrl: String): Boolean {
        return postRepository.createPost(
            Post(
                imageUrl = imageUrl,
                userId = userId,
                description = request.description,
                timestamp = System.currentTimeMillis()
            )
        )
    }
    suspend fun getPostDetails(ownUserId: String, postId: String): PostResponse? {
        return postRepository.getPostDetails(ownUserId, postId)
    }

    suspend fun getPostForFollows(
        ownUserId: String, page: Int = 0, pageSize: Int = Constants.default_post_page_size
    ): List<PostResponse> {
        return postRepository.getPostByFollows(ownUserId, page, pageSize)
    }

    suspend fun getPostsForProfile(
        ownUserId: String,
        userId: String, page: Int = 0,
        pageSize: Int = Constants.default_post_page_size
    ): List<PostResponse> {
        return postRepository.getPostsForProfile(ownUserId,userId, page, pageSize)
    }

    suspend fun getPost(postId: String): Post? = postRepository.getPost(postId)

    suspend fun deletePost(postId: String) {
        postRepository.deletePost(postId)
    }
}
