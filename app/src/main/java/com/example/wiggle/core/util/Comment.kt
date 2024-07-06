package com.example.wiggle.core.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.example.wiggle.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.wiggle.core.domain.model.CommentM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WComment(
    comment: CommentM,
    imageLoader: ImageLoader,
    onLikeClick: () -> Unit = {},
    onLikedByClick: () -> Unit = {},
    modifier:Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.fillMaxHeight(0.5f),
        //  elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.medium,
        //  colors = CardDefaults.cardColors(containerColor = Color.Gray),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            //comment row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberImagePainter(data = comment.profilePictureUrl, imageLoader = imageLoader),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(50.dp)
                        .width(50.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(verticalArrangement = Arrangement.Top)
                {
                    //username and time
                    Row {
                        Text(
                            text = comment.username,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = comment.formattedTime,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                    }
                    //comment and read more
                    Column(modifier = Modifier.padding(vertical=1.dp)) {
                        Text(
                            text = if (expanded) comment.comment else comment.comment.take(3 * 30), // Display first 3 lines
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            //   .clickable { expanded = !expanded },
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 12.sp
                        )
                        if (comment.comment.length > 3 * 30) {
                            Text(
                                text = if(!expanded){"Read more"} else {"Read less"},
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .clickable { expanded = !expanded },
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                //icon and like count
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            tint = if (comment.isLiked) {
                                MaterialTheme.colorScheme.primary
                            } else Color.Gray,
                            contentDescription = if (comment.isLiked) {
                                stringResource(id = R.string.unlike)
                            } else stringResource(id = R.string.like),
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(
                                    onClick = {
                                        onLikeClick()
                                    })

                        )
                        Text(
                            text = "${comment.likeCount}",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 9.sp,
                            modifier = Modifier.fillMaxWidth().clickable
                            { onLikedByClick() }
                        )
                    }
                }

                }
            }
        }
    }

