package org.jcastrejon.features.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState: StateFlow<RegistrationState> = _uiState.asStateFlow()

    private val _onEvent = MutableSharedFlow<RegistrationEvent>()
    val onEvent = _onEvent.asSharedFlow()

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            isValid = password.isNotBlank() && _uiState.value.confirmPassword.isNotBlank()
        )
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            isValid = confirmPassword.isNotBlank() && _uiState.value.password.isNotBlank()
        )
    }

    fun onSessionCheckBoxChanged(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(authenticateEverySession = enabled)
    }

    fun onButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val error = fieldValidators.firstNotNullOfOrNull { validator ->
                validator(uiState.value.password, uiState.value.confirmPassword)
            }
            if (error != null) {
                _onEvent.emit(RegistrationEvent.RegistrationError(error))
            } else {
                _onEvent.emit(RegistrationEvent.RegistrationCompleted)
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
