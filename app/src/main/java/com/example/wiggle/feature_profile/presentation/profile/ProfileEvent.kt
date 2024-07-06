package com.example.wiggle.feature_profile.presentation.profile

import com.example.wiggle.core.domain.model.PostM

sealed class ProfileEvent{
    data class GetProfile(val userId: String): ProfileEvent()
    data class LikePost(val postId: String): ProfileEvent()
    //data class DeletePost(val post: PostM): ProfileEvent()
//    object DismissLogoutDialog: ProfileEvent()
//    object ShowLogoutDialog: ProfileEvent()
//    object Logout: ProfileEvent()

}
