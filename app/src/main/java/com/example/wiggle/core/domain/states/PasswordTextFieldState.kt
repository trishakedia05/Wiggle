package com.example.wiggle.core.domain.states

import com.example.wiggle.core.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)
