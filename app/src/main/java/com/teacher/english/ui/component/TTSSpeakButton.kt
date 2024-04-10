package com.teacher.english.ui.component

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teacher.english.R
import com.teacher.english.ui.component.button.RoundedBorderButton

@Composable
fun TTSSpeakButton(
    tts: TextToSpeech,
    text: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
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
                        painter = painterResource(id = R.drawable.baseline_record_voice_over_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "다시 듣기",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.hex_FFFFFF),
                        textAlign = TextAlign.Center
                    )
                }
            }) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            }
    }
}