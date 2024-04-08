package com.teacher.english.ui.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.teacher.english.domain.extensions.sizeToWidthDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnglishProgressBar(
    progressColor: Int,
    backgroundColor: Int,
    percent: MutableState<Int>,
    height: Dp,
    modifier: Modifier = Modifier
) {
    var size by remember { mutableStateOf(Size.Zero) }
    val widthDp = size.sizeToWidthDp(density = LocalDensity.current)

    Box(
        modifier = modifier
            .background(
                color = Color.Transparent
                ,shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 24.dp)
            .height(height + 8.dp)
    ) {
        Box(modifier = Modifier
            .background(
                color = colorResource(id = backgroundColor)
                ,shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .onSizeChanged { newSize ->
                size = newSize.toSize()
            }
            .height(height + 8.dp)
        ) {
            val progressWidth = (widthDp - 16.dp) * (percent.value / 100.0f)  // Account for both paddings

            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(id = progressColor),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(20.dp))
                    .width(progressWidth)  // Use calculated width
                    .padding(start = 8.dp, end = 8.dp)  // Apply padding on both sides
                    .height(height)
            )
        }
    }
}