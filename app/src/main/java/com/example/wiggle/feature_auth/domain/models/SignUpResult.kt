package com.example.wiggle.feature_auth.domain.models

import AuthError
import com.example.wiggle.core.util.SimpleResource

data class SignUpResult(
        val emailError: AuthError? = null,
        val usernameError: AuthError? = null,
        val passwordError: AuthError? = null,
        val result: SimpleResource? = null
)