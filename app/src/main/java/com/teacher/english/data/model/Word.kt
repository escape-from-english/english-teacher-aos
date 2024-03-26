package com.teacher.english.data.model

data class Word(
    val name: String,
    val meaning: String,
    val status: String? = null,
    val listType: ListType? = null
)
