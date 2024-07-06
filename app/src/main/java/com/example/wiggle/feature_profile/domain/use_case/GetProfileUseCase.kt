package com.example.wiggle.feature_profile.domain.use_case

import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_post.domain.repository.PostRepository
import com.example.wiggle.feature_profile.domain.models.Profile
import com.example.wiggle.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(
    private val repository: ProfileRepository
){
    suspend operator fun invoke(userId: String) : Resource<Profile>
    {
        return repository.getProfile(userId)
    }
}