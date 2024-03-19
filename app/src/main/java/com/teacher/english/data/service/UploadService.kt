package com.teacher.english.data.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @Multipart
    @POST("/v1/words/excels")
    suspend fun uploadFile(@Part excelFile: MultipartBody.Part)

}