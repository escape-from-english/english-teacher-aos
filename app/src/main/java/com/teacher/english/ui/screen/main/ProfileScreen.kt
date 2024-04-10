package com.teacher.english.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.teacher.english.R
import com.teacher.english.data.model.EnglishListItem
import com.teacher.english.data.model.ListType
import com.teacher.english.data.model.SnackBarMessage
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.model.Word
import com.teacher.english.ui.component.button.RoundedBorderButton
import com.teacher.english.ui.component.dialog.ButtonText
import com.teacher.english.ui.component.dialog.EnglishDialog
import com.teacher.english.ui.component.list.EnglishListView
import com.teacher.english.ui.component.snackbar.SnackBarState
import com.teacher.english.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    mainViewModel: MainViewModel,
    snackBarState: SnackBarState
) {
    val teamEditState = remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    val myPageListItems = listOf<EnglishListItem>(
        EnglishListItem(
            title = "Week: ${userProfile.weekNumber}",
            iconResource = null,
            onClick = {
            },
            listType = ListType.SINGLE_TEXT
        ),
        EnglishListItem(
            title = "Team: ${userProfile.selectedTeamId}",
            iconResource = R.drawable.baseline_sync_24,
            onClick = {
                isDialogOpen = true
            },
            listType = ListType.CLICKABLE_SINGLE_TEXT
        )
    )
    val errorText = mainViewModel.errorText.collectAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val (notiArea, profileArea, settingArea) = createRefs()
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(70.dp)
                .constrainAs(profileArea) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 60.dp)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(2.dp))
            Image(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(id = R.drawable.user_profile),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "${userProfile.name}님",
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = colorResource(
                    id = R.color.hex_FFFFFF
                )
            )
            RoundedBorderButton(width = 87.dp,
                height = 26.dp,
                radius = 32.dp,
                borderColor = colorResource(
                    id = R.color.hex_575757
                ),
                content = {
                    Text(
                        text = "프로필 관리",
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.hex_D4D4D4),
                        textAlign = TextAlign.Center
                    )
                },
                surfaceColor = Color.Transparent,
                onClick = {
                    //TODO 프로필 관리 페이지 이동
                }
            )
        }
        EnglishListView(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(settingArea) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(profileArea.bottom)
                },
            listItems = myPageListItems
        )
    }
    if (isDialogOpen) {
        Dialog(
            onDismissRequest = {
                isDialogOpen = false
            }) {
            ConstraintLayout(
                modifier = Modifier
                    .width(350.dp)
                    .height(210.dp)
                    .padding(24.dp)
                    .background(
                        Color.DarkGray,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                val (textFieldCom, sendButton) = createRefs()
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .constrainAs(textFieldCom) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top, margin = 16.dp)
                        },
                    value = teamEditState.value,
                    onValueChange = { newText ->
                        teamEditState.value = newText
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        textColor = Color.Black
                    ),
                    label = { Text("Input a teamId") })
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.hex_FFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .constrainAs(sendButton) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(textFieldCom.bottom, margin = 16.dp)
                        },
                    onClick = {
                        isDialogOpen = false
                        mainViewModel.changeTeamId(teamEditState.value.toLongOrNull() ?: 1L)
                        teamEditState.value = ""
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "변경",
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600,
                        color = colorResource(id = R.color.hex_252525)
                    )
                }
            }
        }
    }
    if (errorText.value != "") {
        snackBarState.showSnackBar(
            SnackBarMessage(
                message = errorText.value
            )
        )
    }
}