package org.jcastrejon.user

class CreateUser(
    private val userManager: UserManager
) {

    suspend operator fun invoke(password: String, authenticateEachSession: Boolean) =
        userManager.create(
            User(
                password = password,
                authenticateEachSession = authenticateEachSession
            )
        )
}