package com.example.wiggle.feature_auth.presentation.signup

import com.example.wiggle.core.util.UiText

data class SignupState(
    val successful : Boolean? = null,
    val message: UiText? = null,
    val isLoading: Boolean = false
)
