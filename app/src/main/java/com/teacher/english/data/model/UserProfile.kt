package com.teacher.english.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UserProfile(
    val name: String = ""
)
