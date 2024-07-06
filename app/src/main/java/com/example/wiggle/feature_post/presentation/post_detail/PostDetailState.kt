package com.example.wiggle.feature_post.presentation.post_detail
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.domain.model.PostM

data class PostDetailState(
    val post: PostM? = null,
    val comments: List<CommentM> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false
)
