package com.plcoding.response

data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val profilePictureUrl: String,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean,
    val bannerUrl: String?
    //val topSkillLinks: List<String>,
)