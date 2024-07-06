package com.example.wiggle.core.domain.model

data class UserItem(
    val userId: String,
    val username: String,
    val profilePictureUrl : String,
    val bio: String,
    val isFollowing: Boolean
){
    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            username = username,
            isFollowing = isFollowing
        )
    }
}