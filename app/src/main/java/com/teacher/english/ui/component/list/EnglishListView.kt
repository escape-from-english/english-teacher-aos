package com.teacher.english.ui.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teacher.english.data.model.EnglishListItem
import com.teacher.english.data.model.ListType

@Composable
fun <T> EnglishListView(
    modifier: Modifier,
    listItems: List<T>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp)
    ) {
        items(listItems) { listItem ->
            when (listItem) {
                is EnglishListItem -> {
                    when (listItem.listType) {
                        ListType.CLICKABLE_SINGLE_TEXT -> {
                            SingleLineTextView(
                                title = listItem.title,
                                iconResource = listItem.iconResource,
                                fontWeight = FontWeight.W500,
                                height = 50.dp,
                                horizontalPadding = 28.dp,
                                onClick = listItem.onClick
                            )
                        }

                        ListType.SINGLE_TEXT -> {
                            SingleLineTextView(
                                title = listItem.title,
                                iconResource = listItem.iconResource,
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                                fontColor = listItem.fontColor,
                                height = 28.dp,
                                horizontalPadding = 28.dp,
                                onClick = listItem.onClick
                            )
                        }
                        else -> {
                            SingleLineTextView(
                                title = listItem.title,
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                                fontColor = listItem.fontColor,
                                height = 28.dp,
                                horizontalPadding = 28.dp,
                                onClick = listItem.onClick
                            )
                        }
                    }
                }
            }
        }
    }
}