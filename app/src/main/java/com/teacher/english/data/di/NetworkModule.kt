package com.teacher.english.data.di

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.teacher.english.data.GsonDateFormatAdapter
import com.teacher.english.data.RestAPIInterceptor
import com.teacher.english.data.service.EnglishService
import com.teacher.english.data.service.UploadService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

const val CONNECT_TIMEOUT = 15L
const val WRITE_TIMEOUT = 15L
const val READ_TIMEOUT = 15L
const val SERVICE_URL = "http://3.39.240.48:8080"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .registerTypeAdapter(Date::class.java, GsonDateFormatAdapter())
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun providesVehicleControlRestAPIInterceptor(
        @ApplicationContext context: Context
    ) = RestAPIInterceptor(context, "application/json")

    @Singleton
    @Provides
    fun provideEnglishService(
        application: Application,
        restAPIInterceptor: RestAPIInterceptor
    ): EnglishService {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(
                okhttpClient(application) {
                    addInterceptor(restAPIInterceptor)
                }
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EnglishService::class.java)
    }

    @Singleton
    @Provides
    fun provideMultipartUploadService(
        application: Application
    ): UploadService {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(
                okhttpClient(application) {
                }
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UploadService::class.java)
    }

    private fun okhttpClient(
        application: Application,
        options: (OkHttpClient.Builder.() -> Unit)? = null
    ) =
        OkHttpClient.Builder().apply {
            cache(Cache(application.cacheDir, 10 * 1024 * 1024))
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            followSslRedirects(true)
            retryOnConnectionFailure(true)
            options?.invoke(this)
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }.build()
}

interface ServiceQualifier {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EnglishService

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MultipartEnglishService
}