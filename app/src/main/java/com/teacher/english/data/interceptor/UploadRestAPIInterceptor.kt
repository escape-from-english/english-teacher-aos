package com.teacher.english.data.interceptor

import com.teacher.english.data.repository.PreferenceStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.coroutines.CoroutineContext

class UploadRestAPIInterceptor(
    private val preferenceStorage: PreferenceStorage
) : Interceptor, CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

    companion object {
        private const val KEY_HEADER_AUTH = "Authorization"
        const val BEARER = "Bearer"
        var bearerToken: String? = null
    }

    init {
        launch {
            preferenceStorage.accessToken.collectLatest {
                bearerToken = it
            }
        }
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (bearerToken == null) {
            chain.proceed(
                chain.request().newBuilder()
                    .method(request.method, request.body)
                    .build()
            )
        } else {
            chain.proceed(
                chain.request().newBuilder()
                    .method(request.method, request.body)
                    .header(KEY_HEADER_AUTH, "$BEARER $bearerToken")
                    .build()
            )
        }
    }
}