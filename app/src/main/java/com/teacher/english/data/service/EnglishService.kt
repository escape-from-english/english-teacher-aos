package com.teacher.english.data.service

import com.teacher.english.data.model.LoginRequest
import com.teacher.english.data.model.LoginResponse
import com.teacher.english.data.model.WordData
import com.teacher.english.data.model.Words
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EnglishService {
    @POST("/v1/words/texts")
    suspend fun addWords(@Body words: Words)

    @GET("/v1/words/status/not-solved/random")
    suspend fun getQuestion(): WordData

    @GET("/v1/words/status/not-solved/existence")
    suspend fun isExistQuestion(): Boolean

    @POST("/v1/member/sign-in")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}