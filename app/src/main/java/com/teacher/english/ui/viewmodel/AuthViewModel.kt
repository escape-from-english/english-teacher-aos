package com.teacher.english.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.teacher.english.data.model.UserProfile
import com.teacher.english.data.repository.AuthRepository
import com.teacher.english.data.repository.EnglishRepository
import com.teacher.english.data.repository.PreferenceStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceStorage: PreferenceStorage
): ViewModel() {
    val isLoading = MutableStateFlow(true)

    fun setLoadingState() {
        viewModelScope.launch {
            isLoading.tryEmit(true)
            delay(100)
            isLoading.tryEmit(false)
        }
    }
    fun login(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginAuthorize(name).collect {
                it.data?.let { response ->
                    if (response.accessToken == null) {

                    } else {
                        preferenceStorage.setAccessToken(
                            response.accessToken
                        )
                        preferenceStorage.setUserProfile(
                            UserProfile(
                                name = name
                            )
                        )
                    }
                }
            }
        }
    }

    fun userProfileState() = preferenceStorage.userProfile
}