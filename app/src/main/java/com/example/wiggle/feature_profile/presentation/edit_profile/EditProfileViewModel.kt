package com.example.wiggle.feature_profile.presentation.edit_profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wiggle.R
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_profile.domain.models.UpdateProfileData
import com.example.wiggle.feature_profile.domain.use_case.ProfileUseCases
import com.example.wiggle.feature_profile.presentation.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri: State<Uri?> = _profilePictureUri

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }
    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(isLoading = true)
            val result = profileUseCases.getProfile(userId)
            when (result) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.SnackbarEvent(
                                UiText.StringResource(R.string.error_couldnt_load_profile)
                            )
                        )
                        return@launch
                    }
                    _usernameState.value = usernameState.value.copy(
                        text = profile.username
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio
                    )

                    _profileState.value = profileState.value.copy(
                        profile = profile,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent(result.uiText ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }
    private fun updateProfile() {
        viewModelScope.launch {
            val result = profileUseCases.updateProfile(
                updateProfileData = UpdateProfileData(
                    username = usernameState.value.text,
                    bio = bioState.value.text,
                ),
                profilePictureUri = profilePictureUri.value,
                bannerUri = bannerUri.value
            )
            when (result) {
                is Resource.Success -> {
                    Log.d("tage","update")
                    _eventFlow.emit(UiEvent.SnackbarEvent(UiText.StringResource(R.string.updated_profile)))
                    //_eventFlow.emit(UiEvent.NavigateUp)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }
    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredBio -> {
                _bioState.value = _bioState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.CropProfilePicture -> {
                _profilePictureUri.value = event.uri
            }
            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }
            is EditProfileEvent.UpdateProfile -> {
                updateProfile()
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateUp)
                }

            }

            else -> {}
        }
    }

}