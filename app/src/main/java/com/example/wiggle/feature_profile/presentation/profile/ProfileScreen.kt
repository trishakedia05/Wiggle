package com.example.wiggle.feature_profile.presentation.profile

import Screen
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.model.User
import com.example.wiggle.core.presentation.components.Post
import com.example.wiggle.core.presentation.components.StandardScaffold
import com.example.wiggle.feature_profile.presentation.profile.components.BannerSection
import com.example.wiggle.feature_profile.presentation.profile.components.ProfileHeaderSection
import com.example.wiggle.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.presentation.util.asString
import com.example.wiggle.core.util.toPx
import com.example.wiggle.feature_post.presentation.person_list.PostEvent
import kotlinx.coroutines.flow.collectLatest


@Composable
fun WProfileScreen(
    userId: String? = null,
    navController: NavController,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    snackbarHostState: SnackbarHostState,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel())
{
    val pagingState = viewModel.pagingState.value
    val lazyListState = rememberLazyListState()

    val toolbarState = viewModel.toolbarState.value
    val iconHorizontalCenterLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (profilePictureSize / 4f).toPx() -
                SpaceSmall.toPx()) / 2f
    val iconSizeExpanded = 35.dp
    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }
    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember {
        bannerHeight + profilePictureSize
    }
    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }
    val state = viewModel.state.value

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if(delta > 0f && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                viewModel.setToolbarOffsetY(newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                ))
                viewModel.setExpandedRatio((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx())
                return Offset.Zero
            }
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is PostEvent.OnLiked -> {

                }
                else -> {}
            }
        }
    }
    StandardScaffold(navController) {
            Box(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection))
            {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment=CenterHorizontally,
                    state = lazyListState
                ) {
                    item{
                        Spacer(modifier = Modifier.height(
                            toolbarHeightExpanded - profilePictureSize / 2f
                        ))
                    }
                    item {
                        state.profile?.let { profile ->
                            ProfileHeaderSection(
                                user = User(
                                    userId= profile.userId,
                                    profilePictureUrl = profile.profilePictureUrl,
                                    username = profile.username,
                                    bio = profile.bio,
                                    followerCount = profile.followerCount,
                                    followingCount = profile.followingCount,
                                    postCount = profile.postCount
                                ),
                                isOwnProfile = profile.isOwnProfile,
                                isFollowing = profile.isFollowing,
                                onFollowClick = { } ,
                                onEditClick = {navController.navigate(Screen.EditProfileScreen.route + "/${profile.userId}")},

                                )
                        }

                    }
                    items(pagingState.items.size) { index ->
                        val post = pagingState.items[index]
                        if (index >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                            viewModel.loadNextPosts()
                        }
                        Spacer(
                            modifier = Modifier
                                .height(SpaceMedium)
                        )
                        Log.d("posttag","$post\n")
                        Post(
                            post = post,
                            imageLoader = imageLoader,
                            onPostClick = {
                                onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                            },
                            onCommentClick = {
                                onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")
                            },
                            onLikeClick = {
                                viewModel.onEvent(ProfileEvent.LikePost(post.id))
                            },
                            isLiked = post.isLiked,
                            onShareClick = {
                              //  context.sendSharePostIntent(post.id)
                            },
                           // onDeleteClick = {
                               // viewModel.onEvent(ProfileEvent.DeletePost(post))
                            //}
                        )

                    }

                }

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    state.profile?.let {profile ->
                        Log.d("bannerurl","${profile.bannerUrl}")
                        BannerSection(
                            bannerUrl = profile.bannerUrl,
                            imageLoader = imageLoader,
                            modifier = Modifier
                                .height(
                                    (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                        minimumValue = toolbarHeightCollapsed,
                                        maximumValue = bannerHeight
                                    )
                                ))
                        Log.d("ppurl","${profile.profilePictureUrl}")
                        Image(

                            painter = rememberImagePainter(data = profile.profilePictureUrl,imageLoader=imageLoader),
                            contentDescription = stringResource(id = R.string.profile_image),
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .graphicsLayer {
                                    translationY = -profilePictureSize.toPx() / 2f -
                                            (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                                    transformOrigin = TransformOrigin(
                                        pivotFractionX = 0.5f,
                                        pivotFractionY = 0f
                                    )
                                    val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .size(profilePictureSize)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = CircleShape
                                )
                        )
                    }

                }

            }
    }
}




