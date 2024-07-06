package com.example.wiggle.feature_activity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.wiggle.core.domain.model.Activity
import com.example.wiggle.core.presentation.components.StandardScaffold
import com.example.wiggle.core.presentation.ui.theme.SpaceMedium
import com.example.wiggle.core.util.DateFormatUtil
import com.example.wiggle.feature_activity.presentation.components.ActivityItem
import kotlin.random.Random

@Composable
fun WActivityScreen(
    navController: NavController,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StandardScaffold(
            navController = navController,){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
            contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(activities.itemCount) { index ->
                val activity = activities[index]
                activity?.let {
                    ActivityItem(
                        activity = Activity(
                            activity.userId,
                            activityType = activity.activityType,
                            formattedTime = activity.formattedTime,
                            parentId = activity.parentId,
                            username = activity.username
                        ),
                        onNavigate = onNavigate
                    )
                }
                if(index<19){
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
        if(state.isLoading){
            CircularProgressIndicator()
        }
}
}