package com.teacher.english.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.data.model.UserProfile
import com.teacher.english.ui.component.list.EnglishListView
import com.teacher.english.ui.viewmodel.MainViewModel

@Composable
fun WordsScreen(
    mainViewModel: MainViewModel,
    userProfile: UserProfile
) {
    val listItem = mainViewModel.englishListFlow.collectAsState()
    LaunchedEffect(key1 = Unit) {
        mainViewModel.getEnglishWordsList(userProfile.weekNumber ?: 0)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (contents, title) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(title){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 24.dp)
                },
            text = "단어 목록"
        )
        EnglishListView(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(contents) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                },
            listItems = listItem.value
        )

    }
}