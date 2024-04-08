package com.teacher.english.ui.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.R

@Composable
fun EnglishSelectionDialog(
    modifier: Modifier = Modifier,
    leftText: String,
    leftIconResourceId: Int,
    rightText: String,
    rightIconResourceId: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (dialog, dialogTail) = createRefs()
        Row(modifier = Modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(size = 32.dp)
            )
            .constrainAs(dialog) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            IconButton(
                modifier = Modifier
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 32.dp)
                    )
                    .size(width = 76.dp, height = 78.dp)
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                onClick = onLeftClick
            ) {
                Column {
                    Image(
                        painter = painterResource(
                            id = leftIconResourceId
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = leftText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600,
                        color = colorResource(
                            id = R.color.hex_FFFFFF
                        )
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 32.dp)
                    )
                    .size(width = 76.dp, height = 78.dp)
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                onClick = onRightClick
            ) {
                Column {
                    Image(
                        painter = painterResource(
                            id = rightIconResourceId
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = rightText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )
                }
            }
        }
        Image(
            painter = painterResource(
                id = R.drawable.bubble_tail
            ),
            contentDescription = null,
            modifier = Modifier
                .size(width = 28.dp, height = 8.dp)
                .constrainAs(dialogTail) {
                    top.linkTo(dialog.bottom)
                    start.linkTo(dialog.start)
                    end.linkTo(dialog.end)
                }
        )
    }
}