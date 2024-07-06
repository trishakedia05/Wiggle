package com.example.wiggle.core.presentation.util

import com.example.wiggle.core.util.Event
import com.example.wiggle.core.util.UiText


sealed class UiEvent: Event(){
    data class SnackbarEvent(val uiText: UiText): UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin: UiEvent()
}