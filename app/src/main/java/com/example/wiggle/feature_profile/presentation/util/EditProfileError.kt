package com.example.wiggle.feature_profile.presentation.util

import com.example.wiggle.core.util.Error

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
