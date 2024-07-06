package com.example.wiggle.feature_activity.data.remote

import com.example.wiggle.core.data.dto.response.ApiResponse
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Constants
import com.example.wiggle.feature_activity.data.remote.dto.ActivityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {
    @GET("/api/activity/get")
    suspend fun getActivities(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<ActivityDto>


    companion object {
        const val BASE_URL = "http://192.168.221.1:8001"
    }
}