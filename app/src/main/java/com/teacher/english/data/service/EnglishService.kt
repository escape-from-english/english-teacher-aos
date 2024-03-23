package com.teacher.english.data.service

import com.teacher.english.data.model.EnglishResponse
import com.teacher.english.data.model.Word
import com.teacher.english.data.model.WordData
import com.teacher.english.data.model.Words
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EnglishService {
    @POST("/v1/words/texts")
    suspend fun addWords(@Body words: Words)

    @GET("/v1/words/status/not-solved/random")
    suspend fun getQuestion(): WordData

    @GET("/v1/words/status/not-solved/existence")
    suspend fun isExistQuestion(): Boolean
}