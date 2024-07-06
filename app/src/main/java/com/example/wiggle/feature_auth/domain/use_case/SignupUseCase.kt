package com.example.wiggle.feature_auth.domain.use_case

import com.example.wiggle.core.domain.util.ValidationUtil
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_auth.domain.models.SignUpResult
import com.example.wiggle.feature_auth.domain.repository.AuthRepository

class SignupUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password:String
    ): SignUpResult
    {
        val emailError = ValidationUtil.validateEmail(email)
        val usernameError = ValidationUtil.validateUsername(username)
        val passwordError = ValidationUtil.validatePassword(password)

        if(emailError != null || usernameError != null || passwordError != null) {
            return SignUpResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
            )
        }

        val result = repository.signup(email.trim(), username.trim(), password.trim())

        return SignUpResult(
            result = result
        )
    }
}