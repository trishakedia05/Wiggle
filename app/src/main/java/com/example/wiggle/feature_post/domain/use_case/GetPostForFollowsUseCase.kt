package com.example.wiggle.feature_post.domain.use_case

import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForFollowsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        page: Int,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<PostM>> {
        return repository.getPostsForFollows(page, pageSize)
    }
}