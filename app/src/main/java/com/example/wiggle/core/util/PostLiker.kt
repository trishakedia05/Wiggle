package com.example.wiggle.core.util

import com.example.wiggle.core.domain.model.PostM


interface PostLiker {

    suspend fun toggleLike(
        posts: List<PostM>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<PostM>) -> Unit
    )
}