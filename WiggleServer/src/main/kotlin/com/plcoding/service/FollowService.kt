package com.plcoding.service

import com.plcoding.data.requests.FollowReq
import com.plcoding.repository.follow.FollowRepository

class FollowService (
    private val followRepository: FollowRepository
){
  suspend fun followUserIfExists(request : FollowReq,followingUserId: String) : Boolean {
     return  followRepository.followUser(
          followingUserId,
          request.followedUserId)
  }
    suspend fun unfollowUserIfExists(followedUserId: String,followingUserId: String) : Boolean {
        return  followRepository.unfollowUserIfExists(
            followingUserId,
            followedUserId)
    }
}