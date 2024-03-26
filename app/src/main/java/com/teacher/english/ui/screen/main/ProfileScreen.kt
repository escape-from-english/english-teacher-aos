package com.teacher.english.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.data.model.UserProfile

@Composable
fun ProfileScreen(
    userProfile: UserProfile
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (nameText, contents) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(nameText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "이름: ${userProfile.name}")
        Column(
            modifier = Modifier
                .constrainAs(contents) {
                    top.linkTo(nameText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "Weeek: ${userProfile.weekNumber}")
        }

    }
}