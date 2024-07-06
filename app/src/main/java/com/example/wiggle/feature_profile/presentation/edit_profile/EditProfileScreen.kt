package com.example.wiggle.feature_profile.presentation.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.R
import com.example.wiggle.core.presentation.components.StandardScaffold
import com.example.wiggle.core.presentation.components.StandardTextField
import com.example.wiggle.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.components.StandardToolbar
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.presentation.util.asString
import com.example.wiggle.core.util.CropActivityResultContract
import com.example.wiggle.feature_profile.presentation.util.EditProfileError
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WEditProfileScreen(
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureSizeLarge
) {
    val profileState = viewModel.profileState.value

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
    }
    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f, 2f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropBannerImage(it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if(it == null) {
            return@rememberLauncherForActivityResult
        }
        cropProfilePictureLauncher.launch(it)
    }
    val bannerImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if(it == null) {
            return@rememberLauncherForActivityResult
        }
        cropBannerImageLauncher.launch(it)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.UpdateProfile)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.save_changes),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ){
            BannerEditSection(
                bannerImage = rememberImagePainter(data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl,imageLoader=imageLoader),
                profileImage = rememberImagePainter(data =  viewModel.profilePictureUri.value ?: profileState.profile?.profilePictureUrl, imageLoader = imageLoader),
                profilePictureSize = profilePictureSize,
                onBannerClick = {
                    bannerImageGalleryLauncher.launch("image/*")
                },
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.usernameState.value.text,
                hint = stringResource(id = R.string.username),
                error = when(viewModel.usernameState.value.error)
                {
                    is EditProfileError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty) }
                    else -> ""
                },
                leadingIcon = Icons.Default.Person,
                onValueChange = {
                    viewModel.onEvent(EditProfileEvent.EnteredUsername(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.bioState.value.text,
                hint = stringResource(id = R.string.your_bio),
                error = when(viewModel.bioState.value.error)
                {
                    is EditProfileError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty) }
                    else -> ""
                },
                singleLine = false,
                maxLines = 3,
                leadingIcon = Icons.Default.Description,
                onValueChange = {
                    viewModel.onEvent(EditProfileEvent.EnteredBio(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Spacer(modifier = Modifier.height(SpaceLarge))
        }

    }
}
}

@Composable
fun BannerEditSection(
    bannerImage: Painter,
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ) {
        Image(
            painter = bannerImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() }
        )
        Image(
            painter = profileImage,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
                .clickable { onProfilePictureClick() }

        )
    }
}
