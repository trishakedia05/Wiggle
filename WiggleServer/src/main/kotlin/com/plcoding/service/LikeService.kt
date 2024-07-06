package com.plcoding.service

import com.plcoding.data.util.ParentType
import com.plcoding.repository.follow.FollowRepository
import com.plcoding.repository.likes.LikesRepository
import com.plcoding.repository.user.UserRepository
import com.plcoding.response.UserResponseItem
import java.security.PrivateKey

class LikeService(
    private val likesRepository: LikesRepository,
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {
    suspend fun likeParent(userId: String,parentId:String,parentType: Int):Boolean{
       // print("\n \n likerep ${likesRepository.likeParent(userId,parentId,parentType)} \n \n")
        return likesRepository.likeParent(userId,parentId,parentType)
    }
    suspend fun unlikeParent(userId: String,parentId:String,parentType: Int):Boolean{
        return likesRepository.unlikeParent(userId,parentId,parentType)
    }
    suspend fun deleteLikesForParent(parentId: String){
        likesRepository.deleteLikesForParent(parentId)
    }
    suspend fun getUsersWhoLikedParent(parentId: String,userId: String): List<UserResponseItem>{
        val userIds = likesRepository.getLikesForParent(parentId).map { it.userId }
        val users = userRepository.getUsers(userIds)
        return users.map { user ->
            val followingByUser = followRepository.getFollowsByUser(userId)
            val isFollowing = followingByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                userId=user.id,
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )

        }
    }
}