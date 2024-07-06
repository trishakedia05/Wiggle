package com.example.wiggle.feature_post.presentation.main_feed

import com.example.wiggle.core.domain.model.PostM

data class MainFeedState(
    val posts: List<PostM> = emptyList(),
    val isLoadingFirstTime: Boolean = false,
    val isLoadingNewPosts: Boolean = false,
    val page: Int = 0
)