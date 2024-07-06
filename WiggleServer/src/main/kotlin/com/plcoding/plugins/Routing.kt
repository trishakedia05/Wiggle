package com.plcoding.plugins

import com.plcoding.data.models.Activity
import com.plcoding.repository.follow.FollowRepository
import com.plcoding.repository.user.UserRepository
import com.plcoding.routes.*
import com.plcoding.service.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Application.configureRouting() {
   // val userRepository : UserRepository by inject()
    val userService : UserService by inject()
    val followService : FollowService by inject()
    val postService : PostService by inject()
    val likeService : LikeService by inject()
    val activityService : ActivityService by inject()
    val commentService : CommentService by inject()
    //val followRepository : FollowRepository by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
        routing {
            //auth routes
            createUserRoutes(userService)
            loginUser(userService, jwtIssuer, jwtAudience, jwtSecret)
            authenticate()
            //user routes
            searchuser(userService)
            getUserProfile(userService)
            //follow routes
            followUser(followService, activityService)
            unfollowUser(followService)
            //post routes
            createPost(postService)
            getPostForFollows(postService)
            deletePost(postService, likeService, commentService)
            getPostForProfile(postService)
            updateUserProfile(userService)
            getPostDetails(postService)
            //like routes
            likeRoute(likeService, activityService)
            unlikeRoute(likeService)
            getLikesForParent(likeService)
            //comment routes
            commentRoute(commentService, activityService)
            getCommentsForPost(commentService)
            deleteComment(commentService, likeService)
            //activity routes
            getActivities(activityService)
            staticFiles("/static", dir = (File("build/resources/main/static")))
        }
}
