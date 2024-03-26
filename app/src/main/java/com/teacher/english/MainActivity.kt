package com.teacher.english

import com.teacher.english.ui.component.snackbar.EnglishTeacherSnackBar
import com.teacher.english.ui.component.snackbar.rememberSnackBarState
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.teacher.english.data.model.SnackBarMessage
import com.teacher.english.data.model.UserProfile
import com.teacher.english.ui.screen.main.MainScreen
import com.teacher.english.ui.component.GlobalNavigationBar
import com.teacher.english.ui.navigate.MainNavRoutes
import com.teacher.english.ui.screen.login.LoginScreen
import com.teacher.english.ui.theme.EnglishTheme
import com.teacher.english.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint(ComponentActivity::class)
class MainActivity : Hilt_MainActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            val userProfileState = authViewModel.userProfileState().collectAsState(
                initial = UserProfile()
            )
            val isLoading = authViewModel.isLoading.collectAsState(initial = true)
            authViewModel.setLoadingState()
            EnglishTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.hex_000000),
                    shadowElevation = 0.dp
                ) {
                    val snackBarMessageState: MutableState<SnackBarMessage?> = remember {
                        mutableStateOf(null)
                    }
                    val snackBarState =
                        rememberSnackBarState(snackBarMessage = snackBarMessageState)
                    val pagerState =
                        rememberPagerState(initialPage = MainNavRoutes.Quiz.ordinal)
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackBarState.snackBarHostState) { _ ->
                                EnglishTeacherSnackBar(
                                    snackBarMessage = snackBarMessageState.value
                                )
                            }
                        },
                        content = {
                            if (isLoading.value) {
                                Box(
                                    modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            } else {
                                if (userProfileState.value.name == "") {
                                    LoginScreen(
                                        authViewModel = authViewModel
                                    )
                                } else {
                                    MainScreen(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .navigationBarsPadding()
                                            .padding(bottom = 84.dp)
                                            .background(color = colorResource(id = R.color.hex_000000)),
                                        snackBarState = snackBarState,
                                        pagerState = pagerState,
                                        userProfile = userProfileState.value
                                    )
                                }
                            }
                        },
                        bottomBar = {
                            if (userProfileState.value.name != "") {
                                GlobalNavigationBar(
                                    modifier = Modifier
                                        .background(color = colorResource(id = R.color.hex_000000))
                                        .fillMaxWidth()
                                        .navigationBarsPadding()
                                        .height(84.dp)
                                        .padding(
                                            top = 6.dp,
                                            bottom = 14.minus(4).dp, // 14dp의 padding값, 버튼사이 간격이 8dp
                                            start = 14.minus(4).dp, // 14dp에서 4dp를 제거하면 8dp 추가 없이 버튼 간격이 동일
                                            end = 14.dp
                                        ),
                                    pagerState = pagerState
                                )
                            }
                        })
                }
            }
        }
    }
}
