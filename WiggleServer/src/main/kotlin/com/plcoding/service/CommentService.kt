package com.plcoding.service

import com.plcoding.data.models.Comment
import com.plcoding.data.requests.CreateCommentReq
import com.plcoding.repository.comments.CommentRepository
import com.plcoding.repository.user.UserRepository
import com.plcoding.response.CommentResponse
import com.plcoding.util.Constants

class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
){
    suspend fun createComment(createCommentReq: CreateCommentReq,userId: String) : ValidationEvent {
        createCommentReq.apply {
            if(comment.isBlank() || postId.isBlank()){
                return ValidationEvent.ErrorFieldEmpty
            }
            if(comment.length > Constants.max_comment_length){
                return ValidationEvent.ErrorCommentTooLong
            }
        }
        val user = userRepository.getUserById(userId) ?: return ValidationEvent.UserNotFound
        commentRepository.createComment(
            Comment(
                username = user.username,
                profileImageUrl = user.profileImageUrl,
                likeCount = 0,
                comment = createCommentReq.comment,
                userId = userId,
                postId = createCommentReq.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvent.Success
    }
    suspend fun deleteComment(commentId: String) : Boolean{
        return commentRepository.deleteComment(commentId)
    }
    suspend fun getCommentById(commentId: String) :Comment?{
        return commentRepository.getComment(commentId)
    }
    suspend fun getCommentsForPost(postId: String,ownUserId: String): List<CommentResponse>{
        return commentRepository.getCommentsForPost(postId,ownUserId)
    }

    suspend fun deleteCommentsForPost(postId: String) {
        commentRepository.deleteCommentsFromPost(postId)

    }
//    suspend fun getComment(commentId: String) : Comment? {}





    sealed class ValidationEvent{
        object ErrorFieldEmpty : ValidationEvent()
        object ErrorCommentTooLong : ValidationEvent()
        object UserNotFound: ValidationEvent()
        object Success : ValidationEvent()
    }
}