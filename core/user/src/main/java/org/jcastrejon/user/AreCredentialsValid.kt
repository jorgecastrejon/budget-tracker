package org.jcastrejon.user

class AreCredentialsValid(
    private val userManager: UserManager
) {

    suspend operator fun invoke(password: String): Boolean =
        userManager.get()?.password == password
}
