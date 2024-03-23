package com.teacher.english.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.Word
import com.teacher.english.data.model.Words
import com.teacher.english.data.repository.EnglishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val englishRepository: EnglishRepository
): ViewModel() {

    private val _randomWord: MutableStateFlow<LoadingState<Word>> =
    MutableStateFlow(LoadingState.success(Word("단어를 가져와 주세요", "")))
    val randomWord = _randomWord

    private val _isEnglishWordExist: MutableStateFlow<LoadingState<Boolean>> =
        MutableStateFlow(LoadingState.success(null))
    val isEnglishWordExist = _isEnglishWordExist

    fun uploadFile(excelFile: ByteArray?, type: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            englishRepository.uploadExcelFile(excelFile, type).collect {

            }
        }
    }

    fun uploadWordList(words: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            englishRepository.uploadWordList(Words(
                words
            )).collect {
            }
        }
    }

    fun getRandomWord() {
        viewModelScope.launch(Dispatchers.IO) {
            englishRepository.isExistRandomWord().collect {
               if (it is LoadingState.Success) {
                   if (it.data == true) {
                       englishRepository.getRandomWord().collect {
                           _randomWord.value = it
                       }
                   }
               }
            }
        }
    }

    fun isEnglishWordExist() {
        viewModelScope.launch(Dispatchers.IO) {
            englishRepository.isExistRandomWord().collect {
                _isEnglishWordExist.value = it
            }
        }
    }
}