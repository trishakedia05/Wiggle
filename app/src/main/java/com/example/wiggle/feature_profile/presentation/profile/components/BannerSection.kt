package com.example.wiggle.feature_profile.presentation.profile.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wiggle.R
import com.example.wiggle.core.util.toPx

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    bannerUrl: String? = null,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        Log.d("this is banner","$bannerUrl")
        Image(
            painter = rememberImagePainter(data = bannerUrl,
                imageLoader = imageLoader),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
    }
}