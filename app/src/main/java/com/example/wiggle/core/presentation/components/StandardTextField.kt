package com.example.wiggle.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.wiggle.R
import com.example.wiggle.core.presentation.ui.theme.IconSizeMedium

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun StandardTextField(
//    modifier: Modifier = Modifier,
//    isError: Boolean = false,
//    text: String = "",
//    hint: String = "",
//    keyboardType: KeyboardType = KeyboardType.Text,
//    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
//  //  isPasswordVisible: Boolean = false,
//    onValueChange: (String) -> Unit
//){
//
//
//    TextField(value = text,
//        onValueChange = onValueChange,
//        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//        isError = isError,
//        singleLine = true,
//        trailingIcon = {
//                       if (isPasswordToggleDisplayed) {
//                           IconButton(onClick = { isPasswordVisible = !isPasswordVisible})
//                           {
//                            Icon(imageVector = if(isPasswordVisible){ Icons.Filled.Visibility } else { Icons.Filled.VisibilityOff},
//                                contentDescription = "password visibility")
//                           }
//                       }
//        } ,
//        placeholder = {
//            Text(text = hint,
//                style =MaterialTheme.typography.bodyMedium) }, modifier=modifier.fillMaxWidth())
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 35,
    error: String = "",
    style: TextStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val containerColor = FilledTextFieldTokens.ContainerColor.toColor()
        TextField(
            value = text,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            maxLines = maxLines,
            textStyle = style,
            placeholder = { Text(text = hint,
                    style = MaterialTheme.typography.bodyLarge) },
            isError = error != "",
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed)
            {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null)
            {
                val icon: @Composable () -> Unit = {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    ) }
                icon
            } else null,
            trailingIcon = if(isPasswordToggleDisplayed)
            {
                val icon: @Composable () -> Unit = {
                    IconButton(onClick = { onPasswordToggleClick(!isPasswordVisible) },
                        modifier = Modifier
//                            .semantics {
//                                testTag = TestTags.PASSWORD_TOGGLE
//                            }
                    ) { Icon(imageVector = if (isPasswordVisible) { Icons.Filled.Visibility }
                                        else { Icons.Filled.VisibilityOff },
                            tint = Color.White,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.password_visible_content_description)
                            } else {
                                stringResource(id = R.string.password_hidden_content_description)
                            })
                    }
                }
                icon
            } else null,
            modifier = Modifier
                .fillMaxWidth()
//                .semantics {
//                    testTag = TestTags.STANDARD_TEXT_FIELD
//                }
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth())
        }
    }

}