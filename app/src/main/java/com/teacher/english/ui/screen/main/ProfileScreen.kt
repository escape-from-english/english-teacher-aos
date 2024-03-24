package com.teacher.english.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProfileScreen(
    name: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (nameText) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(nameText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "이름: $name")

    }
}