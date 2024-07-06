package com.example.wiggle.feature_post.domain.use_case

import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_post.domain.repository.PostRepository

class GetPostDetailsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): Resource<PostM> {
        return repository.getPostDetails(postId)
    }
}