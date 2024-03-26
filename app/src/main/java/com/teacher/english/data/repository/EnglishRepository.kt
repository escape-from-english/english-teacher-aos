package com.teacher.english.data.repository

import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.Word
import com.teacher.english.data.model.Words
import com.teacher.english.data.model.WordsData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface EnglishRepository {
    suspend fun uploadExcelFile(byteArray: ByteArray?, type: String?): Flow<LoadingState<Nothing>>
    suspend fun uploadWordList(words: Words): Flow<LoadingState<Nothing>>
    suspend fun getRandomWord(): Flow<LoadingState<Word>>
    suspend fun isExistRandomWord(): Flow<LoadingState<Boolean>>
    suspend fun getWordsList(weekNumber: Int): Flow<LoadingState<List<Word>>>
}