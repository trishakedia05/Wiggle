package com.example.wiggle.feature_post.data.remote.dto

import com.example.wiggle.core.domain.model.UserItem

data class UserItemDto(
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