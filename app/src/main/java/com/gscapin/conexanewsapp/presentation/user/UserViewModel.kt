package com.gscapin.conexanewsapp.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscapin.conexanewsapp.domain.model.New
import com.gscapin.conexanewsapp.domain.model.User
import com.gscapin.conexanewsapp.domain.useCases.GetUsersUseCase
import com.gscapin.conexanewsapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}

@HiltViewModel
class UserViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            kotlin.runCatching {
                getUsersUseCase()
            }.onSuccess {
                _uiState.value = UserUiState.Success(users = it)
            }.onFailure {
                _uiState.value = UserUiState.Error(it.message.toString())
            }
        }
    }

}