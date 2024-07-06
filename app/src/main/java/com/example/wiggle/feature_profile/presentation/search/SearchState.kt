package com.example.wiggle.feature_profile.presentation.search

import com.example.wiggle.core.domain.model.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
