package com.plcoding.repository.activity

import com.plcoding.data.models.Activity
import com.plcoding.response.ActivityResponse
import com.plcoding.util.Constants

interface ActivityRepository {
    suspend fun getActivitiesForUser(
        userId: String,
        page: Int =0,
        pageSize :Int = Constants.default_activity_page_size
    ) :  List<ActivityResponse>

    suspend fun createActivity(activity: Activity)
    suspend fun deleteActivity(activityId: String) : Boolean
}