package com.teacher.english.ui.component.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SingleLineTextView(
    modifier: Modifier = Modifier,
    title: String,
    iconResource: Int? = null,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight,
    fontColor: Color = Color.White,
    height: Dp,
    horizontalPadding: Dp,
    onClick: () -> Unit?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = horizontalPadding)
            .clickable (enabled = iconResource != null) {
                onClick()
            }
    ) {
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .alpha(if (iconResource == null) 0.4f else 1f),
            textAlign = TextAlign.Start,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = fontColor
        )
        iconResource?.let { resource ->
            Image(
                painter = painterResource(
                    id = resource
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SingleLineTextViewPreview() {
    SingleLineTextView(
        title = "고객센터",
        height = 40.dp,
        fontWeight = FontWeight.W500,
        horizontalPadding = 28.dp,
        onClick = {

        }
    )
}