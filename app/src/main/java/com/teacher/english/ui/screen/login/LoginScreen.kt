package com.teacher.english.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.teacher.english.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel
) {
    val textState = remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (loginButton, loginField) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(loginField) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            TextField(
                modifier = Modifier,
                value = textState.value,
                onValueChange = { newText ->
                    textState.value = newText
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                label = { Text("이름을 입력하세요.") }
            )
        }

        Button(
            modifier = Modifier
                .constrainAs(loginButton){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(loginField.bottom, margin = 16.dp)
                },
            onClick = {
                authViewModel.login(textState.value)
            }) {
            Text(text = "로그인")
            }


    }
}