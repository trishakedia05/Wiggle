package com.example.wiggle.feature_profile.domain.use_case

import com.example.wiggle.core.domain.use_case.ToggleFollowStateForUserUseCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val getPostForProfile: GetPostsForProfileUseCase,
    val searchUser: SearchUserUseCase,
    val toggleFollowState: ToggleFollowStateForUserUseCase
)