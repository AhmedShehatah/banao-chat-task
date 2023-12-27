package com.ahmed.banaochattask.ui.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.banaochattask.data.repos.IAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: IAuthRepo) : ViewModel() {

    private val uid = MutableStateFlow("")
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState
    fun signIn(email: String, password: String) {
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            authRepo.signIn(email, password).catch { _uiState.value = AuthUiState.Error }.collect {
                uid.value = it
                _uiState.value = AuthUiState.Success

            }
        }
    }

}

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object Success : AuthUiState()
    data object Error : AuthUiState()


}

