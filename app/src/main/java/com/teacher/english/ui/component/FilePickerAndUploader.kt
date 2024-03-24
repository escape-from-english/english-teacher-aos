package com.teacher.english.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.teacher.english.ui.viewmodel.MainViewModel
import java.io.InputStream

@Composable
fun FilePickerAndUploader(
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("Select and upload an Excel File") }

    // 파일 선택기를 트리거하는 람다 함수
    val pickFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                text = "File Selected: ${it.path}"
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                val byteArray = inputStream?.readBytes()
                mainViewModel.uploadFile(byteArray, context.contentResolver.getType(it))
            }
        }
    )

    Button(
        modifier = modifier,
        onClick = { pickFile.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") }) {
        Text(text = text)
    }
}