package com.plcoding.routes

import com.plcoding.service.ActivityService
import com.plcoding.service.PostService
import com.plcoding.util.Constants
import com.plcoding.util.QueryParams
import com.plcoding.util.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getActivities(
    activityService: ActivityService
){
    authenticate {
        get("/api/activity/get"){
            val page = call.parameters[QueryParams.param_page]?.toIntOrNull() ?:0
            val pageSize = call.parameters[QueryParams.param_page_size]?.toIntOrNull() ?: Constants.default_activity_page_size

            val activities = activityService.getActivitiesForUser(call.userId,page,pageSize)
            call.respond(HttpStatusCode.OK,activities)

        }
    }
}