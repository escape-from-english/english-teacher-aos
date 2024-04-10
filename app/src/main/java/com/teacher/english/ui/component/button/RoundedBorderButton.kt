package com.teacher.english.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBorderButton(
    width: Dp,
    height: Dp,
    radius: Dp,
    surfaceColor: Color,
    borderColor: Color,
    content: @Composable() () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(width)
            .height(height)
            .clickable {
                onClick()
            },
        color = surfaceColor,
        shape = RoundedCornerShape(radius),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(radius))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}