package com.example.wiggle.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall
import com.example.wiggle.core.util.WComment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentDrawer(
    comments: List<CommentM>? = null,
    onLikeClick: () -> Unit = {},
    onLikedByClick: () -> Unit = {},
    isSheetOpen : Boolean = false,
    imageLoader:ImageLoader
)
{
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)}
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { isSheetOpen = true }) {
                Text(text = "Open sheet")
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
                    },
                    onDismissRequest = { isSheetOpen= false}
                )
            }
        }

}