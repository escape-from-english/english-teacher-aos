package com.teacher.english.ui.component.snackbar


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.teacher.english.data.model.SnackBarMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberSnackBarState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackBarMessage: MutableState<SnackBarMessage?>
) = remember(coroutineScope) {
    SnackBarState(
        coroutineScope,
        snackBarHostState,
        snackBarMessage
    )
}

class SnackBarState(
    private val coroutineScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState,
    snackBarMessage: MutableState<SnackBarMessage?>
) {
    private var snackBarMessage by snackBarMessage

    /**
     * # 사용법 예시
     * snackBarState.showSnackBar(
     *     SnackBarMessage(
     *     snackBarIcon = R.drawable.check,
     *     snackBarImage = R.drawable.charge_port_open,
     *     message = stringResource(R.string.charge_port_open)
     *     )
     *  )
     */
    fun showSnackBar(snackBarMessage: SnackBarMessage) {
        coroutineScope.launch {
            if (snackBarHostState.currentSnackbarData != null) {
                this@SnackBarState.snackBarMessage = snackBarMessage
                return@launch
            }
            this@SnackBarState.snackBarMessage = snackBarMessage
            snackBarHostState.showSnackbar(
                snackBarMessage.message,
                duration = snackBarMessage.snackBarDuration,
                withDismissAction = true
            )
        }

    }
}