package org.jcastrejon.features.login.ui

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
import org.jcastrejon.features.login.ui.FormFieldError.FieldsDoNotMatch
import org.jcastrejon.features.login.ui.LoginEvent.Error
import org.jcastrejon.features.login.ui.LoginEvent.Succeed
import org.jcastrejon.user.AreCredentialsValid
import org.jcastrejon.user.IsFingerprintEnabled
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val isFingerPrintEnabled: IsFingerprintEnabled,
    private val areCredentialsValid: AreCredentialsValid
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _onEvent = MutableSharedFlow<LoginEvent>()
    val onEvent = _onEvent.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val isFingerPrintEnabled = isFingerPrintEnabled()
            update { copy(isFingerPrintEnabled = isFingerPrintEnabled) }
        }
    }

    fun onPasswordChanged(password: String) {
        update { copy(password = password, isValid = password.isNotBlank()) }
    }

    fun onButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            update { copy(isLoading = true) }

            val state = uiState.value
            val error = fieldValidators.firstNotNullOfOrNull { it(state.password) }

            val event = when {
                error != null -> Error(error)
                !areCredentialsValid(state.password) -> Error(FieldsDoNotMatch)
                else -> Succeed
            }
            _onEvent.emit(event)

            update { copy(isLoading = false) }
        }
    }

    private fun update(updateOp: LoginState.() -> LoginState) {
        _uiState.value = _uiState.value.updateOp()
    }
}
