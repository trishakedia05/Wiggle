package com.example.wiggle.core.domain.use_case

import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_profile.domain.repository.ProfileRepository

class ToggleFollowStateForUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String, isFollowing: Boolean): SimpleResource {
        return if (isFollowing) {
            repository.unfollowUser(userId)
        } else {
            repository.followUser(userId)
        }
    }
}