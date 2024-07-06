

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wiggle.core.presentation.ui.theme.SpaceSmall



@Preview
@Composable
@Throws(IllegalArgumentException::class)
fun RowScope.StandardBottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    selected: Boolean = false,
    alertCount: Int? = null,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Gray,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    if (alertCount != null && alertCount < 0) {
        throw IllegalArgumentException("Alert count can't be negative")
    }
    val lineLength = animateFloatAsState(
        targetValue = if(selected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        ), label = ""
    )
    NavigationBarItem(
        selected = selected ,
        onClick = onClick,
        modifier= modifier.fillMaxSize(),
        enabled = enabled,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            indicatorColor = Color.White,
            unselectedIconColor = unselectedColor),
        icon =
        {
            Box(modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.7f)
                .padding(SpaceSmall)
                .drawBehind {
                    if (lineLength.value > 0f) {
                        drawLine(
                            color = if (selected) {
                                selectedColor
                            } else {
                                unselectedColor
                            },
                            start = Offset(
                                size.width / 2f - lineLength.value * 15.dp.toPx(),
                                size.height
                            ),
                            end = Offset(
                                size.width / 2f + lineLength.value * 15.dp.toPx(),
                                size.height
                            ),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }


                }
            ) {
                if(icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(30.dp)
                    )
                }
                if (alertCount != null) {
                    val alertText = if (alertCount > 99) {
                        "99+"
                    } else {
                        alertCount.toString()
                    }
                        Surface(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(-5.dp, -3.dp)
                            .size(15.dp)
                            .clip(CircleShape),
                            color=MaterialTheme.colorScheme.primary,
                            shape = CircleShape)
                        {
                            Text(
                                text =alertText,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = if(alertCount>99){7.sp}else{9.sp},
                                modifier = if(alertCount>99){Modifier.padding(vertical = 4.dp)}
                                           else {Modifier.padding(vertical = 2.dp, horizontal = 1.dp)}

                            )
                        }

                    }
                }

        }
    )
}