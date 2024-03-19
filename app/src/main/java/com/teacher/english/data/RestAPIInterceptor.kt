package com.teacher.english.data

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class RestAPIInterceptor(
    private val context: Context,
    private val contentType: String
) : Interceptor {

    companion object {
        private const val KEY_CONTENT_TYPE = "Content-Type"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(
            chain.request().newBuilder()
                .method(request.method, request.body)
                .header(KEY_CONTENT_TYPE, contentType)
                .build()
        )
    }
}