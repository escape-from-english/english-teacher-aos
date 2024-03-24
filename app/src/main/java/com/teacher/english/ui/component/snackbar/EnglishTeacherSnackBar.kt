package com.teacher.english.ui.component.snackbar


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teacher.english.R
import com.teacher.english.data.model.SnackBarMessage

@Composable
fun EnglishTeacherSnackBar(
    modifier: Modifier = Modifier,
    snackBarMessage: SnackBarMessage?
) {
    Snackbar(
        modifier = Modifier
            .padding(
                bottom = if (snackBarMessage?.snackBarImage != null) 192.dp else 576.dp,
                start = 40.dp,
                end = 40.dp
            )
            .defaultMinSize(minHeight = if (snackBarMessage?.snackBarImage != null) 240.dp else 88.dp),
        shape = RoundedCornerShape(20.dp),
        containerColor = colorResource(id = R.color.hex_515151),
        contentColor = colorResource(id = R.color.hex_515151)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (snackBarMessage?.snackBarIcon != null) {
                    Icon(
                        painter = painterResource(id = snackBarMessage.snackBarIcon),
                        contentDescription = "pictogram",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = snackBarMessage?.message ?: "",
                    color = colorResource(id = R.color.hex_FFFFFF)
                )
            }
            snackBarMessage?.snackBarImage?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .wrapContentSize()
                        .width(212.dp)
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = snackBarMessage.snackBarImage),
                    contentDescription = ""
                )
            }

        }
    }
}