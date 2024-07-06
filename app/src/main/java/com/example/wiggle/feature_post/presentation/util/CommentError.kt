package com.example.wiggle.feature_post.presentation.util

import com.example.wiggle.core.util.Error

sealed class CommentError : Error() {
    object FieldEmpty: CommentError()
}
