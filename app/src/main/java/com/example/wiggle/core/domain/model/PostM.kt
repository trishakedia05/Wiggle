package com.example.wiggle.core.domain.model

data class PostM(
    val id: String,
    val userId: String,
    val username: String,
    val imageUrl: String,
    val profilePictureUrl: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean,
    val isOwnPost: Boolean
)
