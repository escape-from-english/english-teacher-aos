package com.teacher.english.data.model

enum class ListType {
    CLICKABLE_SINGLE_TEXT, SINGLE_TEXT;

    companion object {
        fun toListType(listType: String): ListType {
            return when(listType) {
                "NOT_SOLVED" -> CLICKABLE_SINGLE_TEXT
                "SOLVED" -> CLICKABLE_SINGLE_TEXT
                else -> CLICKABLE_SINGLE_TEXT
            }
        }
    }
}