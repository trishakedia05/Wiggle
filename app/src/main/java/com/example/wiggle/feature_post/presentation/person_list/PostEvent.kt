package com.example.wiggle.feature_post.presentation.person_list

import com.example.wiggle.core.util.Event

sealed class PostEvent : Event() {
    object OnLiked: PostEvent()
}