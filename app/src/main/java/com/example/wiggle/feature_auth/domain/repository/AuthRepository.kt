package com.example.wiggle.feature_auth.domain.repository

import AuthError
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_auth.data.remote.request.CreateAccountRequest

interface AuthRepository{
        suspend fun signup(
                email:String,
                username:String,
                password:String
        ) : SimpleResource

        suspend fun login(
                email:String,
                password:String
        ) : SimpleResource

        suspend fun authenticate(): SimpleResource

}