package com.teacher.english.data.model

import androidx.compose.material3.SnackbarDuration

data class SnackBarMessage(
    val snackBarIcon: Int? = null,
    val snackBarImage: Int? = null,
    val snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    val message: String
)
