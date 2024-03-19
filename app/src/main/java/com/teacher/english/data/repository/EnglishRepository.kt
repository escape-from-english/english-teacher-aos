package com.teacher.english.data.repository

import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.Words
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface EnglishRepository {
    suspend fun uploadExcelFile(byteArray: ByteArray?, type: String?): Flow<LoadingState<Nothing>>
    suspend fun uploadWordList(words: Words): Flow<LoadingState<Nothing>>
    suspend fun getRandomWord(): Flow<LoadingState<String>>
    suspend fun isExistRandomWord(): Flow<LoadingState<Boolean>>
}