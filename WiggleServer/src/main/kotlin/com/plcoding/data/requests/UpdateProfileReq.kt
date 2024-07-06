package com.plcoding.data.requests

data class UpdateProfileReq(
    val username: String,
    val bio: String,
    val profileImageChanges: Boolean=false
)