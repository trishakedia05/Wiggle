package com.example.wiggle.core.presentation.components


import Screen
import StandardBottomNavItem
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.NavItem
import com.example.wiggle.core.util.provider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: SnackbarHostState = remember{SnackbarHostState()},
    showBottomBar: Boolean = true,
    bottomNavItems: List<NavItem> = listOf(
        NavItem(
            route = Screen.MainScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home",
            alertCount = 2
        ),
        NavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.AutoMirrored.Outlined.Chat,
            contentDescription = "Chat",
            alertCount = 1000
        ),
        NavItem(route = "-"),
        NavItem(
            route = Screen.FlicksScreen.route,
            icon = Icons.Outlined.Loop,
            contentDescription = "Flicks",
            alertCount = 12
        ),
        NavItem(
            route ="${Screen.ProfileScreen.route}?userId={userId}",
            icon = Icons.Outlined.Person,
            contentDescription = "Profile",
            alertCount = 2
        ),
    ),
    onFabClick: () -> Unit = {navController.navigate(Screen.CreatePostScreen.route)},
    content: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(modifier= Modifier.clip(RoundedCornerShape(bottomEnd = 6.dp, bottomStart = 6.dp)),
                colors= TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary),
                title = { Text(text = "Wiggle" ,
                    modifier = Modifier.padding(horizontal=5.dp,vertical=10.dp),
                    color= Color.Black, fontSize = 26.sp,
                    fontFamily = FontFamily(
                        Font(
                            googleFont = GoogleFont("Kalam"),
                            fontProvider = provider
                        ) ) ) },
                actions =
                {
                    Row {
                        IconButton(onClick = {navController.navigate(Screen.ActivityScreen.route)}) {
                            Icon(
                                painterResource(id = R.drawable.ic_favourite_border),
                                contentDescription = null,
                                Modifier.padding(8.dp),tint = Color.Black) }
                        IconButton(onClick = {navController.navigate(Screen.SettingsScreen.route)}) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = null,
                                Modifier.padding(8.dp),tint = Color.Black) } }
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = state)
        },
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.09f),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    NavigationBar (containerColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    ){
                        bottomNavItems.forEach { bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected =navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                                alertCount = bottomNavItem.alertCount,
                                enabled = bottomNavItem.icon != null,
                            ) {
                                Log.d("routed","current ${navController.currentDestination?.route}")
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    Log.d("routed","bottom ${bottomNavItem.route}")
                                    navController.navigate(bottomNavItem.route)
                                } }
                        }
                    }
                }
            }
                    },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = onFabClick,
                    shape = CircleShape,
                    modifier = Modifier
                        .offset(0.dp, 60.dp)
                        .size(70.dp)
                        .border(BorderStroke(2.dp, Color.Transparent), CircleShape)

                ) {
                    Icon(painter = painterResource(id = R.drawable.add) ,
                        contentDescription ="Add",
                        tint= Color.White,
                        modifier = Modifier.size(60.dp)
                        )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            content()
        }
    }
}