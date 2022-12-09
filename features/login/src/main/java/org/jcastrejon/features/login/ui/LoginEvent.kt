package org.jcastrejon.features.login.ui

sealed class LoginEvent {
    object Succeed: LoginEvent()
    data class Error(val error: FormFieldError): LoginEvent()
}
