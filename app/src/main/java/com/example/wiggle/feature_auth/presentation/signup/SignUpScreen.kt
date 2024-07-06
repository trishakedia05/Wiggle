package com.example.wiggle.feature_auth.presentation.signup
//
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wiggle.R
import com.example.wiggle.core.presentation.components.StandardTextField
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.presentation.util.asString
import com.example.wiggle.core.util.Constants
import kotlinx.coroutines.flow.collectLatest

//
//
@Composable
fun WSignUpScreen(
    navController: NavController,
    state: SnackbarHostState,
    viewModel: SignupViewModel = hiltViewModel())
{
    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val signupState = viewModel.signupState.value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.SnackbarEvent -> {
                    keyboardController?.hide()
                    state.showSnackbar(
                        event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
                else -> ""
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = SpaceLarge, end = SpaceLarge, top = SpaceLarge, bottom = 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(text=emailState.text ,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
                keyboardType = KeyboardType.Email,
                hint = stringResource(id = R.string.login_hint),
                error = when (emailState.error) {
                   is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InvalidEmail -> {
                        stringResource(id = R.string.not_a_valid_email)
                    }
                    else -> ""
                },)
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(text=usernameState.text ,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredUsername(it))},
                keyboardType = KeyboardType.Text,
                hint = stringResource(id = R.string.hint_username),
                error = when (usernameState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_USERNAME_LENGTH)
                    }
                    else -> ""
                })
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(text=passwordState.text ,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
                keyboardType = KeyboardType.Password,
                hint = stringResource(id = R.string.password_hint),
                error =  when (passwordState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id =R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                    }
                    is AuthError.InvalidPassword -> {
                        stringResource(id = R.string.invalid_password)
                    }
                    else -> ""
                },
                isPasswordVisible = passwordState.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(SignupEvent.TogglePasswordVisibility)
                })
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {
                viewModel.onEvent(SignupEvent.Register)
                //add navigation on validation
                             },
                enabled = !signupState.isLoading,
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(1.dp)
            ) {
                Text( text = stringResource(id = R.string.sign_up),
                    color = MaterialTheme.colorScheme.onPrimary)
            }
            if(signupState.isLoading){
                CircularProgressIndicator(modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally))
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_account))
                append(" ")
                val signUpText = stringResource(id = R.string.login)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable { navController.navigate(Screen.LoginScreen.route) }
        )
    }
}
