package com.example.wiggle.screens.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wiggle.core.presentation.components.StandardScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WChatsScreen(navController: NavController)
{
        StandardScaffold(navController) {
            Box(modifier = Modifier.padding(0.dp))
            {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    UserChatItem(userName = "Trisha", lastMsg ="Hello")
                    UserChatItem(userName = "Trisha", lastMsg ="Hi")
                    UserChatItem(userName = "Trisha", lastMsg ="Small")
                    UserChatItem(userName = "Trisha", lastMsg ="Like")
                }

            }
        }
    }

@Composable
fun UserChatItem(
    userName: String,
    //imageUrl: String,
    lastMsg: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(6.dp))
            .fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription =null,modifier=Modifier.padding(start=5.dp) )
        // CircularImage(imageUrl = imageUrl, imageSize = 55.dp)
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(userName, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                lastMsg,
                color = Color.Gray,
                maxLines = 1,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}