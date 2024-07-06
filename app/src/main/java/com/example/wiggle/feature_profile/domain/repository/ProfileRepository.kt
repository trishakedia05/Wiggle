package com.example.wiggle.feature_profile.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_profile.domain.models.Profile
import com.example.wiggle.feature_profile.domain.models.UpdateProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getPostsPaged(
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        userId: String
    ): Resource<List<PostM>>

    suspend fun getProfile(userId: String) : Resource<Profile>
    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun followUser(userId: String): SimpleResource
    suspend fun unfollowUser(userId: String): SimpleResource
    suspend fun searchUser(query: String): Resource<List<UserItem>>
}