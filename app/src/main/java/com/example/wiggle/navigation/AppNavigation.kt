package com.example.bullseye.navigation

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import com.example.wiggle.feature_profile.presentation.profile.WProfileScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.example.wiggle.screens.chat.WChatsScreen
import com.example.wiggle.feature_post.presentation.create_post.WCreatePostScreen
import com.example.wiggle.screens.flicks.WFlicksScreen
import com.example.wiggle.feature_auth.presentation.login.LoginViewModel
import com.example.wiggle.feature_auth.presentation.login.WLoginScreen
import com.example.wiggle.feature_post.presentation.main_feed.WMainScreen
import com.example.wiggle.feature_activity.presentation.WActivityScreen
import com.example.wiggle.feature_post.presentation.create_post.CreatePostViewModel
import com.example.wiggle.feature_profile.presentation.edit_profile.EditProfileViewModel
import com.example.wiggle.feature_profile.presentation.edit_profile.WEditProfileScreen
import com.example.wiggle.feature_profile.presentation.profile.ProfileViewModel
import com.example.wiggle.feature_profile.presentation.search.SearchViewModel
import com.example.wiggle.feature_profile.presentation.search.WSearchScreen
import com.example.wiggle.screens.settings.WSettingsScreen
import com.example.wiggle.feature_auth.presentation.signup.SignupViewModel
import com.example.wiggle.feature_auth.presentation.signup.WSignUpScreen
import com.example.wiggle.feature_auth.presentation.splash.SplashViewModel
import com.example.wiggle.feature_auth.presentation.splash.WSplashScreen
import com.example.wiggle.feature_post.presentation.person_list.WPersonListScreen
import com.example.wiggle.feature_post.presentation.post_detail.PostDetailViewModel
import com.example.wiggle.feature_post.presentation.post_detail.WPostDetailScreen

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AppNavigation(state :SnackbarHostState,imageLoader: ImageLoader) {
    val navController= rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.SplashScreen.route){

        composable(Screen.SplashScreen.route){
            val splashViewModel= hiltViewModel<SplashViewModel>()
            WSplashScreen( navController= navController,
                viewModel = splashViewModel)
        }
        composable(Screen.LoginScreen.route){
            val loginViewModel= hiltViewModel<LoginViewModel>()
            WLoginScreen(navController=navController,
                onNavigate = navController::navigate,
                onLogin = {
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.MainScreen.route)
                },
                state = state, viewModel = loginViewModel)
        }
        composable(Screen.ActivityScreen.route){
            WActivityScreen(
                navController=navController,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,)
        }
        composable(Screen.ChatScreen.route){
            WChatsScreen(navController=navController)
        }
        composable(Screen.MainScreen.route){
            WMainScreen(
                navController =navController,
                imageLoader=imageLoader,
                snackState = state,
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
                )
        }

        composable(Screen.SearchScreen.route){
            val searchViewModel= hiltViewModel<SearchViewModel>()
            WSearchScreen(navController=navController,
                imageLoader = imageLoader,searchViewModel)
        }
        composable(Screen.CreatePostScreen.route){
            val createViewModel= hiltViewModel<CreatePostViewModel>()
            WCreatePostScreen(navController=navController, viewModel = createViewModel, snackbarHostState = state,
                onNavigateUp = navController::navigateUp,  onNavigate = navController::navigate,
                imageLoader=imageLoader)
        }
        composable(Screen.FlicksScreen.route){
            WFlicksScreen(navController=navController)
        }
        composable(route=Screen.ProfileScreen.route + "?userId={userId}" ,
            arguments = listOf(
                navArgument(name="userId"){
                    type= NavType.StringType
                    nullable =true
                    defaultValue = null
                }
            )
        ){navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            Log.d("userID","${userId}")
            val profileViewModel= hiltViewModel<ProfileViewModel>()
            WProfileScreen(
                navController=navController,
                onNavigate = navController::navigate,
                userId = userId ,
                viewModel=profileViewModel,
                imageLoader = imageLoader,
                snackbarHostState = state)
        }
        composable(Screen.EditProfileScreen.route+ "/{userId}" ,
            arguments = listOf(
                navArgument(name="userId"){
                    type= NavType.StringType
                    nullable=true
                    defaultValue = null
                }
            )){
            val editprofileViewModel= hiltViewModel<EditProfileViewModel>()
            WEditProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = state ,
                imageLoader=imageLoader,
                viewModel = editprofileViewModel)
        }

        composable(Screen.SignupScreen.route){
            val signupViewModel= hiltViewModel<SignupViewModel>()
            WSignUpScreen(navController,state,signupViewModel)
        }
        composable(route = Screen.PostDetailScreen.route + "/{postId}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ) {
                    type = NavType.StringType
                })
        )
        {
            val postDetailViewModel= hiltViewModel<PostDetailViewModel>()
            WPostDetailScreen(
                snackbarHostState= state,
                imageLoader=imageLoader,
                navController = navController,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                viewModel = postDetailViewModel
            )
        }
        composable(Screen.SettingsScreen.route){
            //val mainViewModel= hiltViewModel<Main_ViewModel>()
            WSettingsScreen(navController=navController)
        }
        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument("parentId") {
                    type = NavType.StringType
                }
            )
        ) {
            WPersonListScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = state,
                imageLoader = imageLoader
            )
        }

    }
}