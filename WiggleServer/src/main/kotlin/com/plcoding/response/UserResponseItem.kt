package com.plcoding.response

data class UserResponseItem(
    val userId: String,
    val username: String,
    val profilePictureUrl : String,
    val bio: String,
    val isFollowing: Boolean
)