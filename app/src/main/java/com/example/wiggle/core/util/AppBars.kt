package com.example.wiggle.core.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController:NavController
)
{
    TopAppBar(modifier= Modifier.clip(RoundedCornerShape(bottomEnd = 6.dp, bottomStart = 6.dp)),
        colors= TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary),
        title = { Text(text = "Wiggle" ,
            modifier = Modifier.padding(horizontal=5.dp,vertical=10.dp),
            color= Color.Black, fontSize = 26.sp,
            fontFamily = FontFamily(
                Font(
                    googleFont = GoogleFont("Kalam"),
                    fontProvider = provider
                )
            )
        )
        },
        actions =
        {
            Row {
                IconButton(onClick = {navController.navigate(Screen.ActivityScreen.route)}) {
                    Icon(
                        painterResource(id = R.drawable.ic_favourite_border),
                        contentDescription = null,
                        Modifier.padding(8.dp),tint = Color.Black) }
                IconButton(onClick = {navController.navigate(Screen.ChatScreen.route)}) {
                    Icon(
                        painterResource(id = R.drawable.ic_chat),
                        contentDescription = null,
                        Modifier.padding(8.dp),tint = Color.Black) } }
        })
}

@Composable
fun BottomBar(navController:NavController ){
    BottomAppBar(contentPadding = PaddingValues(horizontal=1.dp, vertical = 2.dp),
        modifier= Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)),
        containerColor = MaterialTheme.colorScheme.secondary,
        actions =
        {
            Row (horizontalArrangement = Arrangement.Center){
                IconButton(onClick = { navController.navigate(Screen.MainScreen.route)  },
                    modifier= Modifier.padding(horizontal = 10.dp))
                { Icon(imageVector = Icons.Filled.Home ,
                        contentDescription ="Tournaments" , tint = Color.Black)
                }
                IconButton(onClick = {navController.navigate(Screen.SearchScreen.route)  },
                    modifier= Modifier.padding(horizontal = 10.dp))
                { Icon(imageVector = Icons.Filled.Search ,
                        contentDescription ="Tournaments" ,tint = Color.Black)
                }
                IconButton(onClick = { navController.navigate(Screen.CreatePostScreen.route) },
                    modifier= Modifier.padding(horizontal = 10.dp))
                { Icon(imageVector = Icons.Filled.AddCircle ,
                        contentDescription ="Tournaments" ,tint = Color.Black)
                }
                IconButton(onClick = { navController.navigate(Screen.FlicksScreen.route) },
                    modifier= Modifier.padding(horizontal = 10.dp))
                { Icon(imageVector = Icons.Filled.Loop ,
                        contentDescription ="Tournaments" ,tint = Color.Black)
                }
                IconButton(onClick = { navController.navigate(Screen.ProfileScreen.route) },
                    modifier= Modifier.padding(horizontal = 10.dp))
                { Icon(imageVector = Icons.Filled.AccountCircle ,
                        contentDescription ="Tournaments",tint = Color.Black )
                }
            }


        })

}

