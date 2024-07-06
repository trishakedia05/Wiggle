package com.example.wiggle.feature_auth.data.remote

import com.example.wiggle.core.data.dto.response.ApiResponse
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_auth.data.remote.request.CreateAccountRequest
import com.example.wiggle.feature_auth.data.remote.request.LoginRequest
import com.example.wiggle.feature_auth.data.remote.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/user/create")
    suspend fun signup(
        @Body request: CreateAccountRequest
    ): ApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = "http://192.168.221.1:8001"
    }
}