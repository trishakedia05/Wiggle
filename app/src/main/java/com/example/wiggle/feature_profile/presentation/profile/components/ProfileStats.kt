package com.example.wiggle.feature_profile.presentation.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.User
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge

@Composable
fun ProfileStats(
    user: User,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onFollowClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.border(BorderStroke(1.dp, Color.Gray)),
        horizontalArrangement=Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileNumber(
            number = user.followerCount,
            text = stringResource(id = R.string.followers)
        )
        Spacer(modifier = Modifier.width(SpaceLarge))
        ProfileNumber(
            number = user.followingCount,
            text = stringResource(id = R.string.following)
        )
        Spacer(modifier = Modifier.width(SpaceLarge))
        ProfileNumber(
            number = user.postCount,
            text = stringResource(id = R.string.posts)
        )
        if(!isOwnProfile) {
            Spacer(modifier = Modifier.width(SpaceLarge))
            Button(
                onClick = onFollowClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFollowing) {
                        Color.Red
                    } else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (isFollowing) {
                        stringResource(id = R.string.unfollow)
                    } else stringResource(id = R.string.follow),
                    color = if(isFollowing) {
                        Color.White
                    } else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}