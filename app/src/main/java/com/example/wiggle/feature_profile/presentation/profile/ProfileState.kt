package com.example.wiggle.feature_profile.presentation.profile

import com.example.wiggle.feature_profile.domain.models.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
//    val isLogoutDialogVisible: Boolean = false
)