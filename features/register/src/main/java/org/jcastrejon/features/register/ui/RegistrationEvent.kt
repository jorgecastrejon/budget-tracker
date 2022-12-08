package org.jcastrejon.features.register.ui

sealed class RegistrationEvent {
    object RegistrationCompleted: RegistrationEvent()
    data class RegistrationError(val error: FormFieldError): RegistrationEvent()
}