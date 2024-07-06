package com.example.wiggle.core.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.presentation.ui.theme.SpaceExtraSmall
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.WComment
import com.example.wiggle.feature_post.presentation.post_detail.PostDetailEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(
    post: PostM,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    isLiked: Boolean,
    comments: List<CommentM>? = null,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onLikedByClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {},
    onPostClick: () -> Unit = {}
) {
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Box(
        modifier = modifier
            .padding(SpaceSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .clip(MaterialTheme.shapes.medium)
//                .shadow(5.dp)
                .background(Color.White)
                .clickable {
                    onPostClick()
                }
        ) {
            Spacer(modifier = Modifier.height(SpaceExtraSmall))
            Log.d("tagmy","username:${post.username} \n image:${post.profilePictureUrl}v, ${post.imageUrl}")
            TopRow(username = post.username,
                profilePictureUrl = post.profilePictureUrl,
                imageLoader = imageLoader,
                onUsernameClick = onUsernameClick)
            Spacer(modifier = Modifier.height(SpaceExtraSmall))
            Box(modifier = Modifier
                .size(400.dp)
                .background(Color.Gray)){
                Image(
                    painter = rememberImagePainter(data = post.imageUrl,imageLoader),
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
                    .padding(horizontal = SpaceMedium, vertical = SpaceSmall)
            ) {
                ActionRow(
                    modifier = Modifier.fillMaxWidth(),
                    description = post.description,
                    onLikeClick = onLikeClick,
                    isLiked = isLiked,
                    onCommentClick = onCommentClick,
                    onShareClick = onShareClick,

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
                            post.likeCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(
                            id = R.string.liked_by_x_comments,
                            post.commentCount
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        comments?.count()?.let {
                            items(it) { index ->
                                val comment = comments[index]
                                WComment(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = SpaceLarge,
                                            vertical = SpaceSmall
                                        ),
                                    comment = comment,
                                    onLikeClick = onLikeClick,
                                    onLikedByClick = onLikedByClick,
                                    imageLoader = imageLoader
                                )
                            }
                        }
                    } }
                }, onDismissRequest = { isSheetOpen= false}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    description: String = "",
    iconSize: Dp = 30.dp,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    var isCommentDrawerVisible by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        Row(modifier= modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom)
        {
            //HANDLE READ MORE AND DESC LINES
            Text(
                text = buildAnnotatedString {
                    append("${description}")
                    withStyle(
                        SpanStyle(
                            color = Color.Gray,
                        )
                    ) {
                        append(
                            LocalContext.current.getString(
                                R.string.read_more
                            )
                        )
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Clip,
                maxLines = Constants.MAX_POST_DESCRIPTION_LINES,
                textAlign = TextAlign.Justify
            )
        }
        IconButton(
            onClick = {
                onLikeClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    Log.d("is Liked color","$isLiked")
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray
                },
                contentDescription = if (isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )
        }
        Spacer(modifier = Modifier.width(SpaceSmall))
        IconButton(
            onClick = {
                onCommentClick()},
            modifier = Modifier.size(iconSize)
        )
        {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                contentDescription = stringResource(id = R.string.comment)
            )
        }

        Spacer(modifier = Modifier.width(SpaceSmall))
        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share)
            )
    }
        }
}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    description: String
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {

        EngagementButtons(
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick,
            description = description
        )
    }
}

@Composable
fun TopRow(
    modifier: Modifier = Modifier,
    onShareClick: () -> Unit = {},
    profilePictureUrl : String,
    imageLoader: ImageLoader,
    username: String,
    onUsernameClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = rememberImagePainter(data = profilePictureUrl,imageLoader),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), CircleShape)
                .padding(vertical = 0.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = username,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .clickable {
                    onUsernameClick()
                }
        )
        Spacer(modifier = Modifier.size(width = 120.dp,height = 1.dp))
        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.share),
            )
        }


    }
}