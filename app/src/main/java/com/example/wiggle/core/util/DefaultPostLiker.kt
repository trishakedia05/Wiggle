package com.example.wiggle.core.util

import com.example.wiggle.core.domain.model.PostM


class DefaultPostLiker: PostLiker {

    override suspend fun toggleLike(
        posts: List<PostM>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<PostM>) -> Unit
    ) {
        val post = posts.find { it.id == parentId }
        val currentlyLiked = post?.isLiked == true
        val currentLikeCount = post?.likeCount ?: 0
        val newPosts = posts.map { post ->
            if (post.id == parentId) {
                post.copy(
                    isLiked = !post.isLiked,
                    likeCount = if (currentlyLiked) {
                        post.likeCount - 1
                    } else post.likeCount + 1
                )
            } else post
        }
        onStateUpdated(newPosts)
        when (onRequest(currentlyLiked)) {
            is Resource.Success -> Unit
            is Resource.Error -> {
                val oldPosts = posts.map { post ->
                    if (post.id == parentId) {
                        post.copy(
                            isLiked = currentlyLiked,
                            likeCount = currentLikeCount
                        )
                    } else post
                }
                onStateUpdated(oldPosts)
            }
        }
    }
}