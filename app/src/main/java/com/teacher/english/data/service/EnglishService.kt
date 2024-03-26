package com.teacher.english.data.service

import com.teacher.english.data.model.LoginRequest
import com.teacher.english.data.model.LoginResponse
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.model.WordData
import com.teacher.english.data.model.Words
import com.teacher.english.data.model.WordsData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EnglishService {
    @POST("/v1/words/texts")
    suspend fun addWords(@Body words: Words)

    @GET("/v1/words/status/not-solved/random")
    suspend fun getQuestion(): WordData

    @GET("/v1/words/status/not-solved/existence")
    suspend fun isExistQuestion(): Boolean

    @POST("/v1/members/sign-in")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @GET("/v1/members/profile")
    suspend fun getProfile(): UserProfile

    @GET("/v1/members/progress/complete")
    suspend fun getCompleteWeek(): Boolean

    @GET("/v1/words/status/not-solved")
    suspend fun getNotSolvedWords(): WordsData
    @GET("/v1/words/weeks/{weekNumber}")
    suspend fun getWeekWords(@Path("weekNumber") weekNumber: Int): WordsData
}