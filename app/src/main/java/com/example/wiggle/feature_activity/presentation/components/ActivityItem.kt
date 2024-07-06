package com.example.wiggle.feature_activity.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.Activity
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.feature_activity.domain.ActivityType


@Composable
fun ActivityItem(
    activity: Activity ,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
    ) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color= Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall),
          verticalArrangement = Arrangement.Top
        ) {
            val fillerText = when(activity.activityType) {
                is ActivityType.LikedPost ->
                    stringResource(id = R.string.liked)
                is ActivityType.CommentedOnPost ->
                    stringResource(id = R.string.commented_on)
                is ActivityType.FollowedUser ->
                    stringResource(id = R.string.followed_you)
                is ActivityType.LikedComment ->
                    stringResource(id = R.string.liked)

            }
            val actionText = when(activity.activityType) {
                is ActivityType.LikedPost ->
                    stringResource(id = R.string.your_post)
                is ActivityType.CommentedOnPost ->
                    stringResource(id = R.string.your_post)
                is ActivityType.FollowedUser -> ""
                is ActivityType.LikedComment -> {
                    stringResource(id = R.string.liked)
                }
            }
            Row {
//                Image(
//                    painter = rememberImagePainter(data = activity.userId),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .height(50.dp)
//                        .width(48.dp)
//                        .align(Alignment.Top),
//                    contentScale = ContentScale.FillBounds
//                )
//                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    val annotatedText = buildAnnotatedString {
                            val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
                            pushStringAnnotation(
                                tag = "username",
                                annotation = "username"
                            )
                            withStyle(boldStyle) {
                                append(activity.username)
                            }
                            pop()
                            append(" $fillerText ")
                            pushStringAnnotation(
                                tag = "parent",
                                annotation = "parent"
                            )
                            withStyle(boldStyle) {
                                append(actionText)
                            }
                        }
                        ClickableText(
                                text = annotatedText,
                            modifier= Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(
                                tag = "username",// tag which you used in the buildAnnotatedString
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { annotation ->
                                // Clicked on user
                                onNavigate(
                                    Screen.ProfileScreen.route + "?userId=${activity.userId}"
                                )
                            }
                            annotatedText.getStringAnnotations(
                                tag = "parent",// tag which you used in the buildAnnotatedString
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { annotation ->
                                // Clicked on parent
                                onNavigate(
                                    Screen.PostDetailScreen.route + "/${activity.parentId}"
                                )
                            }
                        }
                    )


                    Row(horizontalArrangement = Arrangement.End,
                        modifier= Modifier.fillMaxWidth()){
                Text(
                    text = activity.formattedTime,
                    textAlign = TextAlign.Right,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )}
                }}
        }
    }
}