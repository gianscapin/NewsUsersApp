package com.gscapin.conexanewsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.domain.useCases.GetNewsUseCase
import com.gscapin.conexanewsapp.repository.NewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val news: List<New>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _iuState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _iuState.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            kotlin.runCatching {
                getNewsUseCase()
            }.onSuccess { news ->
                _iuState.value = NewsUiState.Success(
                    news = news
                )
            }.onFailure { error ->
                _iuState.value = NewsUiState.Error(
                    message = error.message.toString()
                )
            }
        }
    }

    fun getNewsById(id: Int): New? {
        return (_iuState.value as? NewsUiState.Success)?.news?.firstOrNull { it.id == id }
    }

}