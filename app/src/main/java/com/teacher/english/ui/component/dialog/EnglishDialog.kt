package com.teacher.english.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.teacher.english.R

@Composable
fun EnglishDialog(
    height: Dp? = null,
    icon: ImageVector? = null,
    content: @Composable() () -> Unit,
    color: Int,
    contentColor: Int,
    confirmButton: ButtonText,
    dismissButton: ButtonText? = null,
    onDismissRequest: () -> Unit
) {
    val boxModifier = if (height == null) {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black.copy(alpha = 0.5f))
    } else {
        Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.Black.copy(alpha = 0.5f))
    }
    Popup(onDismissRequest = onDismissRequest) {
        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = colorResource(id = color),
                contentColor = colorResource(id = contentColor),
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 280.dp, height = 200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(36.dp))
                    content()
                    Spacer(Modifier.height(24.dp))
                    IconButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .background(
                                color = colorResource(id = R.color.hex_FFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        onClick = confirmButton.onClick
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            icon?.let {
                                Icon(
                                    imageVector = icon, contentDescription = "dialog icon",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                )
                                Spacer(Modifier.width(4.dp))
                            }
                            Text(
                                text = confirmButton.caption,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W600,
                                color = colorResource(id = R.color.hex_252525)
                            )
                        }
                    }
                    if (dismissButton == null) {
                        Spacer(Modifier.height(54.dp))
                    } else {
                        Spacer(Modifier.height(8.dp))
                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            onClick = dismissButton.onClick
                        ) {
                            Text(
                                text = dismissButton.caption,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 56.dp),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W500,
                                color = colorResource(id = R.color.hex_DEDEDE)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

data class ButtonText(
    val caption: String,
    val onClick: () -> Unit
)