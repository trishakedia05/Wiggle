package com.plcoding.repository.follow

import com.plcoding.data.models.Following


interface FollowRepository {

    suspend fun followUser(
        followingUserId : String,
        followedUserId : String
    ) : Boolean
    suspend fun unfollowUserIfExists(
        followingUserId : String,
        followedUserId : String
    ) : Boolean
    suspend fun doesUserFollow(followingUserId: String,followedUserId: String) : Boolean
    suspend fun getFollowsByUser(userId:String) : List<Following>
}