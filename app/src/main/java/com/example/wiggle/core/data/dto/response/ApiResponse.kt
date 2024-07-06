package com.example.wiggle.core.data.dto.response

data class ApiResponse<T>(
    val message: String? = null,
    val successful: Boolean,
    val data: T? = null
)