package com.example.wiggle.feature_auth.data.repository

import android.content.SharedPreferences
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.ui.unit.Constraints
import com.example.wiggle.R
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_auth.data.remote.request.CreateAccountRequest
import com.example.wiggle.feature_auth.data.remote.request.LoginRequest
import com.example.wiggle.feature_auth.data.remote.AuthApi
import com.example.wiggle.feature_auth.domain.repository.AuthRepository
import java.io.IOException
import java.lang.invoke.ConstantCallSite

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository
{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun signup( email:String,
                                 username:String,
                                 password:String): SimpleResource
    {
        val request = CreateAccountRequest(email,username,password)
        return try{
            val response = api.signup(request)
            if(response.successful){
                Resource.Success(Unit)
            } else{
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Log.e("Authdance", "IOException occurred: ${e.message}")
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun login(email:String,
                               password:String): SimpleResource {
        val request = LoginRequest(email,password)
        return try{
            val response = api.login(request)
            if(response.successful) {
                response.data?.let { authResponse ->
                    Log.d("AuthResponse" ,"$authResponse")
                    Log.d("shared","${sharedPreferences.getString(Constants.KEY_USER_ID, "")}")
                sharedPreferences.edit()
                    .putString(Constants.KEY_JWT_TOKEN,authResponse.token)
                    .putString(Constants.KEY_USER_ID,authResponse.userId)
                    .apply()
                }
                Log.d("sharedAfter","${sharedPreferences.getString(Constants.KEY_USER_ID, "")}")
                Resource.Success(Unit)
            } else{
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun authenticate(): SimpleResource {
        return try {
           api.authenticate()
            Resource.Success(Unit)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}
//android:networkSecurityConfig="@xml/network_security_config"