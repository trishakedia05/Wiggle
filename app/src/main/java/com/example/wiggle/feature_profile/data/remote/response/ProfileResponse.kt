package com.example.wiggle.feature_profile.data.remote.response

import com.example.wiggle.feature_profile.domain.models.Profile

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
){
    fun toProfile(): Profile {
        return Profile(
            userId=userId,
            username = username,
            bio = bio,
            followerCount = followerCount,
            followingCount = followingCount,
           postCount =  postCount,
           profilePictureUrl =  profilePictureUrl,
           isOwnProfile =  isOwnProfile,
           isFollowing =  isFollowing,
            bannerUrl = bannerUrl
        )
    }
}