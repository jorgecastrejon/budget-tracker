package org.jcastrejon.features.login.ui

data class LoginState(
    val password: String = String(),
    val isFingerPrintEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
)
