package com.example.wiggle.di

import com.example.wiggle.feature_post.data.remote.PostApi
import com.example.wiggle.feature_profile.data.remote.ProfileApi
import com.example.wiggle.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.wiggle.feature_profile.domain.repository.ProfileRepository
import com.example.wiggle.feature_profile.domain.use_case.GetProfileUseCase
import com.example.wiggle.feature_profile.domain.use_case.ProfileUseCases
import com.example.wiggle.feature_profile.domain.use_case.SearchUserUseCase
import com.example.wiggle.core.domain.use_case.ToggleFollowStateForUserUseCase
import com.example.wiggle.feature_profile.domain.use_case.GetPostsForProfileUseCase
import com.example.wiggle.feature_profile.domain.use_case.UpdateProfileUseCase
import com.google.gson.Gson
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
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ProfileApi.BASE_URL)
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApi: ProfileApi,
        postApi:PostApi,
        gson: Gson
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileApi,gson,postApi)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            getPostForProfile = GetPostsForProfileUseCase(repository),
            searchUser = SearchUserUseCase(repository),
            toggleFollowState = ToggleFollowStateForUserUseCase(repository)
        )
    }
    @Provides
    @Singleton
    fun provideToggleFollowForUserUseCase(repository: ProfileRepository): ToggleFollowStateForUserUseCase {
        return ToggleFollowStateForUserUseCase(repository)
    }


}