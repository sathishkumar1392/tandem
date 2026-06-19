package com.sathish.tandem.feature.community.components

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LikeButton(
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onLikeClick,
        modifier = modifier
    ) {
        Text(
            text = if (isLiked) "👍🏽" else "👍",
            fontSize = 24.sp
        )
    }
}