package org.jcastrejon.database

import kotlinx.coroutines.flow.Flow

interface ReactiveDataSource<T> {
    fun get(): Flow<T?>
    suspend fun put(data: T)
}