package org.jcastrejon.features.register.ui

data class RegistrationState(
    val password: String = String(),
    val confirmPassword: String = String(),
    val authenticateEverySession: Boolean = false,
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
)
