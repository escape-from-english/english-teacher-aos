package com.teacher.english.ui.screen.main

import com.teacher.english.ui.component.snackbar.SnackBarState
import android.speech.tts.TextToSpeech
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.teacher.english.ui.navigate.MainNavRoutes
import com.teacher.english.ui.viewmodel.MainViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    snackBarState: SnackBarState,
    pagerState: PagerState,
    name: String
) {
    val context = LocalContext.current
    var ttsInitState by remember { mutableStateOf(false) }
    val tts = remember {
        TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                ttsInitState = true
            }
        }
    }
    val mainViewModel: MainViewModel = hiltViewModel()

    if (ttsInitState) {
        tts.language = Locale.US
        ttsInitState = false
    }
    // TTS 객체 정리
    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }
    HorizontalPager(
        count = MainNavRoutes.entries.size, state = pagerState, modifier = modifier
    ) { page ->
        when (MainNavRoutes.entries[page]) {
            MainNavRoutes.Quiz -> QuizScreen(
                mainViewModel = mainViewModel,
                tts = tts,
                snackBarState = snackBarState
            )
            MainNavRoutes.Profile -> ProfileScreen(name = name)
        }
    }
}