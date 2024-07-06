package com.example.wiggle.feature_activity.presentation

import com.example.wiggle.core.domain.model.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
)
