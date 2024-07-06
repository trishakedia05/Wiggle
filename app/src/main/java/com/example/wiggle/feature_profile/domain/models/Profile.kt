package com.example.wiggle.feature_profile.domain.models

data class Profile(
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
)