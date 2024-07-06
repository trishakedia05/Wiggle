package com.example.wiggle.core.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val icon: ImageVector? = null,
    val contentDescription: String? = null,
    val alertCount: Int? = null,
)
