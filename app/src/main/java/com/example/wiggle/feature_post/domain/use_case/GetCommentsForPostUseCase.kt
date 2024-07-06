package com.example.wiggle.feature_post.domain.use_case

import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_post.domain.repository.PostRepository


class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): Resource<List<CommentM>> {
        return repository.getCommentsForPost(postId)
    }
}