package org.jcastrejon.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val password: String,
    val authenticateEachSession: Boolean = false,
    val useBiometrics: Boolean = false,
)
