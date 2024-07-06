package com.plcoding.repository.activity

import com.plcoding.data.models.Activity
import com.plcoding.data.models.Following
import com.plcoding.data.models.Post
import com.plcoding.data.models.User
import com.plcoding.response.ActivityResponse
import com.plcoding.service.PostService
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`

class ActivityRepositoryImpl(
    db: CoroutineDatabase
) : ActivityRepository {
    private val users = db.getCollection<User>()
    private val activities = db.getCollection<Activity>()
    override suspend fun getActivitiesForUser(
        userId: String,
        page: Int,
        pageSize: Int):
            List<ActivityResponse>
    {
        val activities = activities.find(Activity::toUserId eq userId)
            .skip(page * pageSize)
            .limit(pageSize)
            .descendingSort(Activity::timestamp)
            .toList()
        val userIds = activities.map { it.byUserId }
        val users = users.find(User::id `in` userIds)
            .toList()
            .associateBy{ it.id}
        return activities.map { activity ->
            val user = users[activity.byUserId]
            ActivityResponse(
                timestamp = activity.timestamp,
                userId = activity.byUserId,
                parentId = activity.parentId,
                type = activity.type,
                username = user?.username ?: "cd ",
                id = activity.id
            )
        }
    }

    override suspend fun createActivity(activity: Activity){
    activities.insertOne(activity)
    }

    override suspend fun deleteActivity(activityId: String): Boolean {
    return activities.deleteOneById(activityId).wasAcknowledged()
    }
}

