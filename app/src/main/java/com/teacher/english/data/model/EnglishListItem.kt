package com.teacher.english.data.model

import androidx.compose.ui.graphics.Color

data class EnglishListItem(
    val title: String,
    val iconResource: Int? = null,
    val fontColor: Color = Color.White,
    val listType: ListType,
    val onClick: () -> Unit
)