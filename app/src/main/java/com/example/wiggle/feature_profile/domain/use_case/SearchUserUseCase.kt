package com.example.wiggle.feature_profile.domain.use_case

import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.util.Resource
import com.example.wiggle.feature_profile.domain.repository.ProfileRepository

class SearchUserUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(query:String): Resource<List<UserItem>>{
        if(query.isBlank()){
            return Resource.Success(data= emptyList())
        }
        return repository.searchUser(query)
    }
}