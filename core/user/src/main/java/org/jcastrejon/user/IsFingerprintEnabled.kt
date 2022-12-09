package org.jcastrejon.user

class IsFingerprintEnabled(
    private val userManager: UserManager
) {
    suspend operator fun invoke(): Boolean =
        userManager.get()?.useBiometrics == true
}