package com.example.wiggle.feature_auth.domain.use_case

import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
)
{
    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}