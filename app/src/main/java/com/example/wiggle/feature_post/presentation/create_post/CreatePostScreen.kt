package com.example.wiggle.feature_post.presentation.create_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wiggle.core.presentation.components.StandardScaffold
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.R
import com.example.wiggle.core.presentation.components.StandardTextField
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.presentation.util.asString
import com.example.wiggle.core.util.CropActivityResultContract
import com.example.wiggle.feature_post.presentation.util.PostConstants
import com.example.wiggle.feature_post.presentation.util.PostDescriptionError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun WCreatePostScreen(navController: NavController,
                      viewModel: CreatePostViewModel = hiltViewModel(),
                      snackbarHostState: SnackbarHostState,
                      onNavigateUp: () -> Unit = {},
                      onNavigate: (String) -> Unit = {},
                      imageLoader: ImageLoader
){
    val imageUri = viewModel.chosenImageUri.value
    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f, 9f)
    ) {
        viewModel.onEvent(CreatePostEvent.CropImage(it))
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        cropActivityLauncher.launch(it)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.uiText.asString(context)
                        )
                    }
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> {}
            }
        }
    }

    StandardScaffold(navController){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(
                            data = uri,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                hint = stringResource(id = R.string.description),
                error = when(viewModel.descriptionState.value.error){
                    is PostDescriptionError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    else -> ""
                },
                singleLine = false,
                maxLines = 5,
                maxLength = PostConstants.MAX_POST_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(
                        CreatePostEvent.EnterDescription(it)                    )
                }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(
                onClick = {viewModel.onEvent(CreatePostEvent.PostImage)},
                enabled = !viewModel.isLoading.value,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                } else {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }
        }
    }
}
