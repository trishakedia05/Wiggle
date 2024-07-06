package com.example.wiggle.feature_auth.domain.models

import AuthError
import com.example.wiggle.core.util.SimpleResource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)