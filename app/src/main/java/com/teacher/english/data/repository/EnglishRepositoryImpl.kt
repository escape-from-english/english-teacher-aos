package com.teacher.english.data.repository

import android.util.Log
import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.Word
import com.teacher.english.data.model.Words
import com.teacher.english.data.model.WordsData
import com.teacher.english.data.service.EnglishService
import com.teacher.english.data.service.UploadService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnglishRepositoryImpl @Inject constructor(
    private val englishService: EnglishService,
    private val uploadService: UploadService

): EnglishRepository {
    override suspend fun uploadExcelFile(byteArray: ByteArray?, type: String?): Flow<LoadingState<Nothing>> = flow {
        emit(LoadingState.loading(null))
        try {
            val requestFile = byteArray?.let {
                it.toRequestBody(type?.toMediaTypeOrNull(), 0, it.size)
            }
            val body = requestFile?.let {
                MultipartBody.Part.createFormData("excelFile", "excel.xlsx", it)
            }
            if (body != null) {
                uploadService.uploadFile(body)
            }
            emit(LoadingState.success(null))
        } catch (e: Exception) {
            emit(LoadingState.error(e, null))
        }
    }

    override suspend fun uploadWordList(words: Words): Flow<LoadingState<Nothing>> = flow {
        emit(LoadingState.loading(null))
        try {
            englishService.addWords(words)
            emit(LoadingState.success(null))
        } catch (e: Exception) {
            emit(LoadingState.error(e, null))
        }
    }

    override suspend fun getRandomWord(): Flow<LoadingState<Word>> = flow {
        emit(LoadingState.loading(null))
        try {
            val getRandomWordResp = englishService.getQuestion()
            Log.d("random ", "$getRandomWordResp")
            emit(LoadingState.success(getRandomWordResp.word))
        } catch (e: Exception) {
            emit(LoadingState.error(e, null))
        }
    }

    override suspend fun isExistRandomWord(): Flow<LoadingState<Boolean>> = flow {
        emit(LoadingState.loading(null))
        try {
            val isExistRandomWordResp = englishService.isExistQuestion()
            emit(LoadingState.success(isExistRandomWordResp))
        } catch (e: Exception) {
            emit(LoadingState.error(e, null))
        }
    }

    override suspend fun getWordsList(weekNumber: Int): Flow<LoadingState<List<Word>>> = flow {
        emit(LoadingState.loading(null))
        try {
            val getWordsResp = englishService.getWeekWords(weekNumber)

            emit(LoadingState.success(getWordsResp.words))
        } catch (e: Exception) {
            emit(LoadingState.error(e, null))
        }
    }

}