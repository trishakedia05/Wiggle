package com.example.wiggle.di

import android.content.SharedPreferences
import com.example.wiggle.feature_auth.data.repository.AuthRepositoryImpl
import com.example.wiggle.feature_auth.data.remote.AuthApi
import com.example.wiggle.feature_auth.domain.repository.AuthRepository
import com.example.wiggle.feature_auth.domain.use_case.AuthenticateUseCase
import com.example.wiggle.feature_auth.domain.use_case.LoginUseCase
import com.example.wiggle.feature_auth.domain.use_case.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    @Provides
    @Singleton
    fun providesAuthRepo(api:AuthApi,
                         sharedPreferences: SharedPreferences) : AuthRepository {
        return AuthRepositoryImpl(api,
            sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSignupUseCae(repository: AuthRepository): SignupUseCase {
        return SignupUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCae(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCae(repository: AuthRepository): AuthenticateUseCase {
        return AuthenticateUseCase(repository)
    }
}