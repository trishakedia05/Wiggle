package com.example.wiggle.feature_profile.presentation.search

import Screen
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.User
import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.presentation.components.StandardScaffold
import com.example.wiggle.core.presentation.components.StandardTextField
import com.example.wiggle.core.presentation.components.UserProfileItem
import com.example.wiggle.core.presentation.ui.theme.IconSizeMedium
import com.example.wiggle.core.presentation.ui.theme.SpaceLarge
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.domain.states.StandardTextFieldState



@Composable
fun WSearchScreen(
    navController: NavController,
    imageLoader: ImageLoader,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.value
    StandardScaffold(navController) {
        Box(modifier = Modifier.padding())
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge)
            ) {
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.searchFieldState.value.text,
                    hint = stringResource(id = R.string.search),
                    error = "",
                    leadingIcon = Icons.Default.Search,
                    onValueChange = {
                        viewModel.onEvent(SearchEvent.Query(it))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.userItems.size) { item ->
                        val userItem = state.userItems[item]
                        UserProfileItem(
                            user = UserItem(
                                userId = userItem.userId,
                                profilePictureUrl = userItem.profilePictureUrl,
                                username = userItem.username,
                                bio = userItem.bio,
                                isFollowing = userItem.isFollowing
                            ),
                            actionIcon = {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(SearchEvent.ToggleFollowState(userItem.userId))
                                    },
                                    modifier = Modifier
                                        .size(IconSizeMedium)
                                ) {
                                    Icon(
                                        imageVector = if (userItem.isFollowing) {
                                            Icons.Default.PersonRemove
                                        } else Icons.Default.PersonAdd,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                    )
                                }
                            }, onItemClick = {
                               // Log.d("new route","${Screen.ProfileScreen.route}?userId=${userItem.userId}}")
                                navController.navigate(Screen.ProfileScreen.route + "?userId=${userItem.userId}")
                            },
                            imageLoader = imageLoader
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                }
            }
            if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}

