package com.example.wiggle.feature_post.presentation.person_list

import com.example.wiggle.core.domain.model.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
