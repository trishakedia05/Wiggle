package com.example.wiggle.feature_post.presentation.util

import com.example.wiggle.core.util.Error

sealed class PostDescriptionError : Error() {
    object FieldEmpty : PostDescriptionError()
}
