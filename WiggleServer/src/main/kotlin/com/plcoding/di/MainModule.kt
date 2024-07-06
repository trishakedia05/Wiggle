package com.plcoding.di

import com.google.gson.Gson
import com.plcoding.repository.activity.ActivityRepository
import com.plcoding.repository.activity.ActivityRepositoryImpl
import com.plcoding.repository.comments.CommentRepository
import com.plcoding.repository.comments.CommentRepositoryImpl
import com.plcoding.repository.follow.FollowRepository
import com.plcoding.repository.follow.FollowRepositoryImpl
import com.plcoding.repository.likes.LikesRepository
import com.plcoding.repository.likes.LikesRepositoryImpl
import com.plcoding.repository.post.PostRepository
import com.plcoding.repository.post.PostRepositoryImpl
import com.plcoding.repository.user.UserRepository
import com.plcoding.repository.user.UserRepositoryImpl
import com.plcoding.service.*
import com.plcoding.util.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainModule = module {
    single {
    val client = KMongo.createClient().coroutine
         client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
    single<LikesRepository> {
        LikesRepositoryImpl(get())
    }
    single<CommentRepository> {
        CommentRepositoryImpl(get())
    }
    single<ActivityRepository> {
        ActivityRepositoryImpl(get())
    }
    single {
        UserService(get(),get())
    }
    single {
        FollowService(get())
    }
    single {
        PostService(get())
    }
    single {
        LikeService(get(),get(),get())
    }
    single {
        CommentService(get(),get())
    }
    single {
        ActivityService(get(),get(),get())
    }
    single { Gson() }
}
