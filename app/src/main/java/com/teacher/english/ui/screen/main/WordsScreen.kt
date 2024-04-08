package com.teacher.english.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.teacher.english.R
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.model.Word
import com.teacher.english.ui.component.button.BottomActionButton
import com.teacher.english.ui.component.dialog.EnglishSelectionDialog
import com.teacher.english.ui.component.list.EnglishListView
import com.teacher.english.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(
    mainViewModel: MainViewModel,
    userProfile: UserProfile
) {
    val listItem = mainViewModel.englishListFlow.collectAsState()
    var isSelectionDialogOpen by remember { mutableStateOf(false) }
    val isAddDialogOpen = remember {
        mutableStateOf(false)
    }
    val textState = remember { mutableStateOf("") }
    val meaningState = remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        mainViewModel.getEnglishWordsList(userProfile.weekNumber ?: 0)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (contents, title, addWordButton) = createRefs()
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
                .navigationBarsPadding()
                .constrainAs(contents) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            listItems = listItem.value
        )
        Column(
            modifier = Modifier
                .width(150.dp)
                .constrainAs(addWordButton) {
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
        ) {

            if (isSelectionDialogOpen) {
                EnglishSelectionDialog(modifier = Modifier
                    .width(150.dp)
                    .height(76.dp)
                    ,
                    leftText = "Excel",
                    leftIconResourceId = R.drawable.baseline_addchart_24,
                    rightText = "Word",
                    rightIconResourceId = R.drawable.baseline_playlist_add_24,
                    onLeftClick = {
                        isSelectionDialogOpen = false
                    },
                    onRightClick = {
                        isAddDialogOpen.value = true
                        isSelectionDialogOpen = false
                    })
            }
            Spacer(modifier = Modifier.height(6.dp))
            // 하단 플로팅 액션 버튼 사용
            BottomActionButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    // 버튼 클릭 시 동작할 내용 구현
                    isSelectionDialogOpen = !isSelectionDialogOpen
                })   
        }

        if (isAddDialogOpen.value) {
            Dialog(
                onDismissRequest = {
                    isAddDialogOpen.value = false
                }) {
                ConstraintLayout(
                    modifier = Modifier
                        .size(350.dp)
                        .padding(24.dp)
                        .background(
                            Color.DarkGray
                        )
                ) {
                    val (textFieldCom, meaningFieldCom, sendButton) = createRefs()
                    TextField(
                        modifier = Modifier
                            .constrainAs(textFieldCom) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top, margin = 16.dp)
                            },
                        value = textState.value,
                        onValueChange = { newText ->
                            textState.value = newText
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            textColor = Color.Black
                        ),
                        label = { Text("Input a word") })
                    TextField(
                        modifier = Modifier
                            .constrainAs(meaningFieldCom) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(textFieldCom.bottom, margin = 16.dp)
                            },
                        value = meaningState.value,
                        onValueChange = { newText ->
                            meaningState.value = newText
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            textColor = Color.Black
                        ),
                        label = { Text("Input a meaning") })

                    Button(
                        modifier = Modifier
                            .constrainAs(sendButton) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(meaningFieldCom.bottom, margin = 16.dp)
                            },
                        onClick = {
                            isAddDialogOpen.value = false
                            mainViewModel.uploadWordList(
                                listOf(Word(textState.value, meaningState.value))
                            )
                        }) {
                        Text(text = "단어 전송")
                    }
                }

            }
        }

    }
}