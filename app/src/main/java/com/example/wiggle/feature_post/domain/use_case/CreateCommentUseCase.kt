package com.example.wiggle.feature_post.domain.use_case

import com.example.wiggle.R
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String, comment: String): SimpleResource {
        if(comment.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.error_field_empty))
        }
        if(postId.isBlank()) {
            return Resource.Error(UiText.unknownError())
        }
        return repository.createComment(postId, comment)
    }
}