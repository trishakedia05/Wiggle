package com.plcoding.data.models

import com.plcoding.response.ProfileResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val email: String,
    val username: String,
    val password: String,
    val profileImageUrl : String,
    val bio: String,
    val followerCount :Int =0,
    val followingCount : Int=0,
    val postCount : Int=0,
    val bannerUrl: String?,

    @BsonId
    val id: String= ObjectId().toString(),
)