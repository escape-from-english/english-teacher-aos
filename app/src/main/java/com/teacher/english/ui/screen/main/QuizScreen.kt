package com.teacher.english.ui.screen.main

import com.teacher.english.ui.component.snackbar.SnackBarState
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.data.model.Word
import com.teacher.english.ui.component.FilePickerAndUploader
import com.teacher.english.ui.viewmodel.MainViewModel
import com.teacher.english.ui.component.TTSSpeakButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    mainViewModel: MainViewModel,
    tts: TextToSpeech,
    snackBarState: SnackBarState
) {
    val randomWord = mainViewModel.randomWord.collectAsState()
    val isAddDialogOpen = remember {
        mutableStateOf(false)
    }
    val isEnglishText = remember {
        mutableStateOf(true)
    }
    val textState = remember { mutableStateOf("") }
    val meaningState = remember { mutableStateOf("") }
    LaunchedEffect(key1 = randomWord.value) {
        tts.speak(randomWord.value.data?.name, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (textField, ttsField, fileUploadField, getRandomWordButton, addWordButton) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(textField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 200.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (isEnglishText.value) randomWord.value.data?.name ?: "" else randomWord.value.data?.meaning ?: "",
                fontSize = 30.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier
                .height(12.dp))
            Button(
                modifier = Modifier,
                onClick = { isEnglishText.value = !isEnglishText.value}) {
                Text(text = "정답 확인")
            }
        }

        TTSSpeakButton(
            tts = tts,
            text = randomWord.value.data?.name ?: "",
            modifier = Modifier
                .constrainAs(ttsField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(textField.bottom, margin = 16.dp)
                }
        )

        Button(
            modifier = Modifier
                .constrainAs(getRandomWordButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(ttsField.bottom, margin = 16.dp)
                },
            onClick = { mainViewModel.getRandomWord()}) {
            Text(text = "랜덤 word 가져오기")
        }

        FilePickerAndUploader(
            modifier = Modifier
                .constrainAs(fileUploadField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(getRandomWordButton.bottom, margin = 16.dp)
                }
            ,
            mainViewModel = mainViewModel
        )

        Button(
            modifier = Modifier
                .constrainAs(addWordButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(fileUploadField.bottom, margin = 16.dp)
                },
            onClick = { isAddDialogOpen.value = true}) {
            Text(text = "단어 추가")
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