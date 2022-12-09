package org.jcastrejon.user

import kotlinx.coroutines.flow.firstOrNull
import org.jcastrejon.database.ReactiveDataSource

class UserManager(
    private val datasource: ReactiveDataSource<User>
) {

    suspend fun get(): User? =
        datasource.get().firstOrNull()?.takeIf { it.password.isNotBlank() }

    suspend fun create(user: User) {
        datasource.put(user)
    }
}