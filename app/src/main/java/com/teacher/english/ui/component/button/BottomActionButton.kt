package com.teacher.english.ui.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomActionButton(
    modifier: Modifier,
    onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Color.Blue, // 버튼의 배경색 설정
        modifier = modifier.padding(16.dp) // 여백 설정
    ) {
        Icon(
            imageVector = Icons.Default.Add, // 버튼에 표시될 아이콘
            contentDescription = "Add" // 스크린 리더기에 읽히는 텍스트
        )
    }
}