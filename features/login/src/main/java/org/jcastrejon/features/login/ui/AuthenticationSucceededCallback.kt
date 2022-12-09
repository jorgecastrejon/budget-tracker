package org.jcastrejon.features.login.ui

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class AuthenticationSucceededCallback(
    private val block: (Boolean) -> Unit
) : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        block(true)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        block(false)
    }

    override fun onAuthenticationFailed() {
        block(false)
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        block(true)
    }
}
