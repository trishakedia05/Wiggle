package com.example.wiggle.feature_post.domain.use_case

data class PostUseCases(
    val getPostForFollows: GetPostsForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails : GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase,
    val createComment: CreateCommentUseCase,
    val toggleLikeForParent: ToggleLikeForParentUseCase,
    val getLikesForParent: GetLikesForParentUseCase
)