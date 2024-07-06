package com.example.wiggle.feature_post.presentation.post_detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.wiggle.core.presentation.components.Post
import com.example.wiggle.core.presentation.components.StandardScaffold
import com.example.wiggle.core.presentation.ui.theme.SpaceExtraSmall
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.states.StandardTextFieldState
import com.example.wiggle.core.presentation.components.ActionRow
import com.example.wiggle.core.presentation.components.SendTextField
import com.example.wiggle.core.presentation.components.TopRow
import com.example.wiggle.core.presentation.ui.theme.SpaceExtraSmall
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.presentation.util.asString
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.WComment
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WPostDetailScreen(
    navController: NavController,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }

                else -> {}
            }
        }

    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardScaffold(
            navController = navController,
            modifier = Modifier.fillMaxWidth(),
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Log.d("postdetail","${state.post}")
                        state.post?.let{
                            Box(
                                modifier = Modifier
                                    .padding(SpaceSmall)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                ) {
                                    Spacer(modifier = Modifier.height(SpaceExtraSmall))
                                    TopRow(
                                        username = state.post.username,
                                        profilePictureUrl = state.post.profilePictureUrl,
                                        imageLoader = imageLoader,
                                        onUsernameClick = { Screen.ProfileScreen.route + "?userId = ${state.post.userId}"}
                                    )
                                    Spacer(modifier = Modifier.height(SpaceExtraSmall))
                                    Box(modifier = Modifier
                                        .size(400.dp)
                                        .background(Color.Gray)){
                                        Image(
                                            painter = rememberImagePainter(data = state.post.imageUrl,imageLoader),
                                            contentDescription = "Post image",
                                            modifier = Modifier
                                                .fillMaxSize()
                                            //  .clip(RectangleShape),
                                            ,alignment = Alignment.Center,
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = SpaceMedium,
                                                vertical = SpaceSmall
                                            )
                                    ) {
                                        ActionRow(
                                            modifier = Modifier.fillMaxWidth(),
                                            isLiked = state.post.isLiked,
                                            description = state.post.description,
                                            onLikeClick = {  viewModel.onEvent(PostDetailEvent.LikePost)},
                                            onCommentClick = { isSheetOpen = true },
                                            onShareClick = { }
                                        )
                                        Spacer(modifier = Modifier.height(SpaceSmall))
                                        Spacer(modifier = Modifier.height(SpaceMedium))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = stringResource(
                                                    id = R.string.liked_by_x_people,
                                                    state.post.likeCount
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.headlineMedium,
                                                modifier = Modifier.clickable {
                                                    onNavigate(Screen.PersonListScreen.route + "/${state.post.id}")

                                                }
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.liked_by_x_comments,
                                                    state.post.commentCount
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.headlineMedium
                                            )
                                        }
                                    }
                                }
                                if (isSheetOpen) {
                                    ModalBottomSheet(
                                        sheetState = sheetState,
                                        shape = RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                                        containerColor = Color.White,
                                        content = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(300.dp) // Set the fixed height here
                                                    .background(Color.White),
                                                contentAlignment = Alignment.TopCenter
                                            ){
                                                Column(modifier = Modifier.fillMaxSize())
                                                {
                                                    LazyColumn(
                                                        modifier = Modifier
                                                            .fillMaxHeight(0.65f)
                                                            .fillMaxWidth()
                                                    ) {
                                                        state.comments.count()?.let {
                                                            items(it) { index ->
                                                                val comment = state.comments[index]
                                                                WComment(
                                                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(
                                                                            horizontal = SpaceLarge,
                                                                            vertical = SpaceSmall
                                                                        ),
                                                                    comment = comment,
                                                                    onLikeClick =    { viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))},
                                                                    onLikedByClick = { onNavigate(Screen.PersonListScreen.route + "/${comment.id}") },
                                                                    imageLoader = imageLoader
                                                                )
                                                            }
                                                        }
                                                    }
                                                    SendTextField(
                                                        state = viewModel.commentTextFieldState.value,
                                                        onValueChange = {
                                                            viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                                                        },
                                                        onSend = {
                                                            viewModel.onEvent(PostDetailEvent.Comment)
                                                        },
                                                        hint = stringResource(id = R.string.enter_a_comment),
                                                        isLoading = viewModel.commentState.value.isLoading,
                                                    )
                                                }

                                            }
                                        },
                                        onDismissRequest = { isSheetOpen= false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}