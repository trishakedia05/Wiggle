package com.example.wiggle.feature_auth.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wiggle.R
import com.example.wiggle.core.domain.states.PasswordTextFieldState
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.feature_auth.domain.use_case.SignupUseCase
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel()
{
    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _onSignup = MutableSharedFlow<Unit>(replay = 1)
    val onSignup = _onSignup.asSharedFlow()

    fun onEvent(event: SignupEvent) {
        when(event) {
            is SignupEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.value
                )
            }
            is SignupEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is SignupEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            is SignupEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            is SignupEvent.Register -> {
                signup()
            }
        }
    }
    private fun signup()
    { viewModelScope.launch {
        _usernameState.value = usernameState.value.copy(error = null)
        _emailState.value = emailState.value.copy(error = null)
        _passwordState.value = passwordState.value.copy(error = null)
        _signupState.value = SignupState(isLoading = true)
        val registerResult = signupUseCase(
            email = emailState.value.text,
            username = usernameState.value.text,
            password = passwordState.value.text
        )
        if(registerResult.emailError != null) {
            _emailState.value = emailState.value.copy(
                error = registerResult.emailError
            )
        }
        if(registerResult.usernameError != null) {
            _usernameState.value = _usernameState.value.copy(
                error = registerResult.usernameError
            )
        }
        if(registerResult.passwordError != null) {
            _passwordState.value = _passwordState.value.copy(
                error = registerResult.passwordError
            )
        }
        when(registerResult.result) {
            is Resource.Success -> {
                _eventFlow.emit(
                    UiEvent.SnackbarEvent(UiText.StringResource(R.string.success_registration))
                )
                _onSignup.emit(Unit)
                _signupState.value = SignupState(isLoading = false)
                _usernameState.value = StandardTextFieldState()
                _emailState.value = StandardTextFieldState()
                _passwordState.value = PasswordTextFieldState()
            }
            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.SnackbarEvent(registerResult.result.uiText ?: UiText.unknownError())
                )
                _signupState.value = SignupState(isLoading = false)
            }
            null -> {
                _signupState.value = SignupState(isLoading = false)
            }
        }
    }
    }

}