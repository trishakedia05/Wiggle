package com.plcoding.data.requests

data class CreateCommentReq(
    val comment : String,
    val postId: String,
)