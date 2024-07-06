package com.example.wiggle.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.wiggle.R
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import androidx.compose.material3.CircularProgressIndicator as CircularProgressIndicator1

@Composable
fun SendTextField(
    state: StandardTextFieldState,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    hint: String = "",
    canSendMessage: Boolean = true,
    isLoading: Boolean = false,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            text = state.text,
            onValueChange = onValueChange,
            style = TextStyle(
                background =MaterialTheme.colorScheme.background ) ,
            modifier = Modifier
                .weight(1f),
            hint = hint,
        )
        if (isLoading) {
            CircularProgressIndicator1(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            IconButton(
                onClick = onSend,
                enabled = state.error == null || !canSendMessage
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    tint = if (state.error == null && canSendMessage) {
                       Color.Blue
                    } else Color.Gray,
                    contentDescription = stringResource(id = R.string.send_comment)
                )
            }
        }
    }
}