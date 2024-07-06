package com.plcoding.repository.follow

import com.mongodb.client.model.Filters.and
import com.plcoding.data.models.Following
import com.plcoding.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class FollowRepositoryImpl(db: CoroutineDatabase) :FollowRepository {
    private val following = db.getCollection<Following>()
    private val users = db.getCollection<User>()
    override suspend fun followUser(
        followingUserId: String,
        followedUserId: String): Boolean
    {
        val doesFollowingUserExist = users.findOneById(followingUserId) !=null
        val doesFollowedUserExist = users.findOneById(followedUserId) != null
        if(!doesFollowedUserExist || !doesFollowingUserExist) {
            return false
        }
            following.insertOne(Following(followingUserId,followedUserId))
        return true
    }

    override suspend fun unfollowUserIfExists(
        followingUserId: String,
        followedUserId: String): Boolean
    {
    val deleteResult = following.deleteOne(
        and(
           Following::followingUserId eq followingUserId,
           Following::followedUserId eq followedUserId,
        )
    )
        return deleteResult.deletedCount>0
    }

    override suspend fun doesUserFollow(followingUserId: String, followedUserId: String): Boolean {
       return following.findOne(
           and(
               Following::followingUserId eq followingUserId,
               Following::followedUserId eq followedUserId,
           )
       ) != null
    }

    override suspend fun getFollowsByUser(userId: String): List<Following> {
            return following.find(
                Following::followingUserId eq userId
            ).toList()
    }
}