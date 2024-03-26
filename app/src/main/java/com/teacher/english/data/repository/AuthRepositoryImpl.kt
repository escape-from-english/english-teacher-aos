package com.teacher.english.data.repository

import android.util.Log
import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.LoginRequest
import com.teacher.english.data.model.LoginResponse
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.service.EnglishService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val englishService: EnglishService
): AuthRepository {
    override suspend fun loginAuthorize(name: String): Flow<LoadingState<LoginResponse>> =
        flow {
            emit(LoadingState.loading(null))
            try {
                val loginAuthorizeResponse =
                    englishService.login(LoginRequest(name = name))
                emit(LoadingState.success(loginAuthorizeResponse))
            } catch (e: Exception) {
                Log.e("Login Error", "${e.printStackTrace()}")
                emit(LoadingState.error(e, null))
            }
        }

    override suspend fun getProfile(): Flow<LoadingState<UserProfile>> =
        flow {
            emit(LoadingState.loading(null))
            try {
                val profileResponse =
                    englishService.getProfile()
                emit(LoadingState.success(profileResponse))
            } catch (e: Exception) {
                Log.e("Login Error", "${e.printStackTrace()}")
                emit(LoadingState.error(e, null))
            }
        }
}