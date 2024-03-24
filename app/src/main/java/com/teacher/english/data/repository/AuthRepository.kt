package com.teacher.english.data.repository

import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginAuthorize(name: String): Flow<LoadingState<LoginResponse>>
}