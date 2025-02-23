package com.example.wiggle.feature_profile.presentation.edit_profile

import android.net.Uri

sealed class EditProfileEvent {
    data class EnteredUsername(val value: String): EditProfileEvent()
    data class EnteredBio(val value: String): EditProfileEvent()
    data class CropProfilePicture(val uri: Uri?): EditProfileEvent()
    data class CropBannerImage(val uri: Uri?): EditProfileEvent()
    object UpdateProfile: EditProfileEvent()
}
