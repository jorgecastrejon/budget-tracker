package org.jcastrejon.features.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jcastrejon.user.CreateUser
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val createUser: CreateUser
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState: StateFlow<RegistrationState> = _uiState.asStateFlow()

    private val _onEvent = MutableSharedFlow<RegistrationEvent>()
    val onEvent = _onEvent.asSharedFlow()

    fun onPasswordChanged(password: String) {
        update {
            copy(
                password = password,
                isValid = password.isNotBlank() && confirmPassword.isNotBlank()
            )
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        update {
            copy(
                confirmPassword = confirmPassword,
                isValid = confirmPassword.isNotBlank() && password.isNotBlank()
            )
        }
    }

    fun onSessionCheckBoxChanged(enabled: Boolean) {
        update { copy(authenticateEverySession = enabled) }
    }

    fun onButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            update { copy(isLoading = true) }

            val state = uiState.value
            val error = fieldValidators.firstNotNullOfOrNull { validator ->
                validator(state.password, state.confirmPassword)
            }

            if (error != null) {
                _onEvent.emit(RegistrationEvent.RegistrationError(error))
            } else {
                createUser(
                    password = state.password,
                    authenticateEachSession = state.authenticateEverySession
                )
                _onEvent.emit(RegistrationEvent.RegistrationCompleted)
            }
            update { copy(isLoading = false) }
        }
    }

    private fun update(updateOp: RegistrationState.() -> RegistrationState) {
        _uiState.value = _uiState.value.updateOp()
    }
}
