package com.teacher.english.ui.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teacher.english.R
import com.teacher.english.data.model.EnglishListItem
import com.teacher.english.data.model.ListType
import com.teacher.english.data.model.LoadingState
import com.teacher.english.data.model.Word
import com.teacher.english.data.model.Words
import com.teacher.english.data.model.WordsData
import com.teacher.english.data.repository.EnglishRepository
import com.teacher.english.data.repository.PreferenceStorage
import com.teacher.english.ui.component.list.EnglishListView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val englishRepository: EnglishRepository,
    private val preferenceStorage: PreferenceStorage
): ViewModel() {

    private val _randomWord: MutableStateFlow<LoadingState<Word>> =
    MutableStateFlow(LoadingState.success(Word("단어를 가져와 주세요", "")))
    val randomWord = _randomWord

    private val _isEnglishWordExist: MutableStateFlow<LoadingState<Boolean>> =
        MutableStateFlow(LoadingState.success(null))
    val isEnglishWordExist = _isEnglishWordExist

    private val _englishListFlow: MutableStateFlow<List<EnglishListItem>> =
        MutableStateFlow(emptyList())
    val englishListFlow = _englishListFlow

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

    fun getEnglishWordsList(weekNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            englishRepository.getWordsList(weekNumber).collect { words ->
                _englishListFlow.tryEmit(words.data?.map { EnglishListItem(
                    title = "${it.name}: ${it.meaning}",
                    listType = ListType.toListType(it.status ?: "NOT_SOLVED"),
                    iconResource = if (it.status == "SOLVED") R.drawable.baseline_check_circle_24 else R.drawable.baseline_check_circle_outline_24,
                    onClick = {

                    }
                )   } ?: emptyList())
            }
        }
    }
}