package com.teacher.english.ui.component

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TTSSpeakButton(
    tts: TextToSpeech,
    text: String,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }) {
            Text("Speak")
        }
    }
}