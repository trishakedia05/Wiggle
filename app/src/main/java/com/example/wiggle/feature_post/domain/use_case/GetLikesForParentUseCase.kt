package com.example.wiggle.feature_post.domain.use_case

import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_post.domain.repository.PostRepository

class GetLikesForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(parentId: String): Resource<List<UserItem>> {
        return repository.getLikesForParent(parentId)
    }
}