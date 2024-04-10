package com.teacher.english.ui.screen.main

import com.teacher.english.ui.component.snackbar.SnackBarState
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.R
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.model.Word
import com.teacher.english.ui.component.FilePickerAndUploader
import com.teacher.english.ui.viewmodel.MainViewModel
import com.teacher.english.ui.component.TTSSpeakButton
import com.teacher.english.ui.component.button.BottomActionButton
import com.teacher.english.ui.component.button.RoundedBorderButton
import com.teacher.english.ui.component.dialog.EnglishSelectionDialog
import com.teacher.english.ui.component.progress.EnglishProgressBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    mainViewModel: MainViewModel,
    tts: TextToSpeech,
    snackBarState: SnackBarState,
    userProfile: UserProfile
) {
    val randomWord = mainViewModel.randomWord.collectAsState()
    val isEnglishText = remember {
        mutableStateOf(true)
    }
    val listItem = mainViewModel.englishListFlow.collectAsState()
    val sizeOfSolvedWords = remember {
        mutableIntStateOf(listItem.value.count { it.iconResource == R.drawable.baseline_check_circle_24 })
        }
    val sizeOfList = mainViewModel.englishWordsCountFlow.collectAsState()
    val progressState = remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        mainViewModel.getEnglishWordCount(userProfile.weekNumber ?: 0)
    }
    LaunchedEffect(key1 = listItem.value) {
        sizeOfSolvedWords.intValue = listItem.value.count { it.iconResource == R.drawable.baseline_check_circle_24 }
        progressState.intValue = ((sizeOfSolvedWords.intValue / sizeOfList.value.toDouble()) * 100.0).toInt()
    }
    LaunchedEffect(key1 = randomWord.value) {
        tts.speak(randomWord.value.data?.name, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (textField, ttsField, fileUploadField, getRandomWordButton, progressBar, progressText) = createRefs()

        EnglishProgressBar(
            progressColor = R.color.hex_5A46FA,
            backgroundColor = R.color.hex_454545,
            percent = progressState,
            height = 10.dp,
            modifier = Modifier
                .constrainAs(progressBar) {
                    top.linkTo(parent.top, margin = 36.dp)
                }
        )
        Text(
            modifier = Modifier
                .constrainAs(progressText) {
                   start.linkTo(progressBar.start, margin = 36.dp)
                   top.linkTo(progressBar.top)
                   bottom.linkTo(progressBar.bottom)
                },
            textAlign = TextAlign.Center,
            text = "${sizeOfSolvedWords.intValue}/${sizeOfList.value}",
            fontSize = 10.sp,
            color = Color.White
        )
        Column(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.hex_1F1F1F),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(16.dp)
                .constrainAs(textField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 200.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center,
                text = randomWord.value.data?.name ?: "",
                fontSize = 30.sp,
                color = Color.White
            )
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .background(
                        color = Color.White
                    )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center,
                text = if (isEnglishText.value) "" else randomWord.value.data?.meaning ?: "",
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(getRandomWordButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 36.dp)
                },
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedBorderButton(
                width = 76.dp,
                height = 76.dp,
                radius = 16.dp,
                surfaceColor = colorResource(id = R.color.hex_4860FF),
                borderColor = colorResource(id = R.color.hex_5A46FA),
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(40.dp),
                            painter = painterResource(id = R.drawable.baseline_check_box_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "정답 확인",
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.hex_FFFFFF),
                            textAlign = TextAlign.Center
                        )
                    }
                }) {
                isEnglishText.value = !isEnglishText.value
                }
            TTSSpeakButton(
                tts = tts,
                text = randomWord.value.data?.name ?: "",
                modifier = Modifier
            )
            RoundedBorderButton(
                width = 76.dp,
                height = 76.dp,
                radius = 16.dp,
                surfaceColor = colorResource(id = R.color.hex_5A46FA),
                borderColor = colorResource(id = R.color.hex_5A46FA),
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(40.dp),
                            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (randomWord.value.data?.name == "단어를 가져와 주세요") "문제 뽑기" else "다음 문제",
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.hex_FFFFFF),
                            textAlign = TextAlign.Center
                        )
                    }
                }) {
                isEnglishText.value = true
                mainViewModel.getRandomWord()
                sizeOfSolvedWords.intValue = sizeOfSolvedWords.intValue + 1
                progressState.intValue = ((sizeOfSolvedWords.intValue / sizeOfList.value.toDouble()) * 100.0).toInt()
            }

        }

    }
}