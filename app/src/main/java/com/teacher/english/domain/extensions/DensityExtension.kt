package com.teacher.english.domain.extensions

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density

// 반응형 UI를 위해 width size check
fun Size.sizeToWidthDp(density: Density) = with(density) { this@sizeToWidthDp.width.toDp() }

fun Size.sizeToHeightDp(density: Density) = with(density) { this@sizeToHeightDp.height.toDp() }
