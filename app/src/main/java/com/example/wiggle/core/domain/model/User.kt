package com.example.wiggle.core.domain.model


data class User(
    val userId: String,
    val profilePictureUrl: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int
)
