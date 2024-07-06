package com.example.wiggle.feature_profile.presentation.search

import com.example.wiggle.core.util.Error
import com.example.wiggle.core.util.UiText

data class SearchError(
    val message: UiText
): Error()
