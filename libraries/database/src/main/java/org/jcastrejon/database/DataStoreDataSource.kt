package org.jcastrejon.database

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class DataStoreDataSource<T>(
    private val dataStore: DataStore<T>
) : ReactiveDataSource<T> {

    override fun get(): Flow<T?> =
        dataStore.data

    override suspend fun put(data: T) {
        dataStore.updateData { data }
    }
}
