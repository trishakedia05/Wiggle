package com.example.wiggle.feature_auth.presentation.signup

sealed class SignupEvent {
    data class EnteredUsername(val value: String): SignupEvent()
    data class EnteredEmail(val value: String): SignupEvent()
    data class EnteredPassword(val value: String): SignupEvent()
    object TogglePasswordVisibility : SignupEvent()
    object Register : SignupEvent()


}