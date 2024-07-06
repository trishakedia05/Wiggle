package com.example.wiggle.core.domain.states

import com.example.wiggle.core.util.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)
