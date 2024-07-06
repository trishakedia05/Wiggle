package com.plcoding.repository.comments

import com.plcoding.data.models.Comment
import com.plcoding.response.CommentResponse

interface CommentRepository {
    suspend fun createComment(comment: Comment) : String
    suspend fun deleteComment(commentId: String) : Boolean
    suspend fun deleteCommentsFromPost(postId: String) : Boolean
    suspend fun getComment(commentId: String) : Comment?
    suspend fun getCommentsForPost(postId: String,ownUserId: String) : List<CommentResponse>
}