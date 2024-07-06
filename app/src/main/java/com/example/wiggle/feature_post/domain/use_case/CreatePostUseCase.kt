package com.example.wiggle.feature_post.domain.use_case

import android.net.Uri
import com.example.wiggle.R
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_post.domain.repository.PostRepository
import com.example.wiggle.feature_post.presentation.util.PostDescriptionError

class CreatePostUseCase(
    private val repository: PostRepository
){
    suspend operator fun invoke(
        description: String,
        imageUri: Uri?
    ): SimpleResource{
        if(imageUri == null) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_no_image_picked)
            )
        }
        if(description.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_description_blank)
            )
        }
        return repository.createPost(description,imageUri)
    }
}