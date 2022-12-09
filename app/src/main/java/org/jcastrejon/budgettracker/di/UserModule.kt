package org.jcastrejon.budgettracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.jcastrejon.budgettracker.cipher.AesCipherProvider
import org.jcastrejon.database.DataStoreDataSource
import org.jcastrejon.database.ReactiveDataSource
import org.jcastrejon.database.crypto.CipherProvider
import org.jcastrejon.database.crypto.Crypto
import org.jcastrejon.database.crypto.CryptoImpl
import org.jcastrejon.database.serializer.SecuredSerialized
import org.jcastrejon.user.CreateUser
import org.jcastrejon.user.User
import org.jcastrejon.user.UserManager
import java.io.File
import java.security.KeyStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserModule {

    private const val SecuredFileName = "secured_file_name_b.json"
    private const val KeyStoreName = "AndroidKeyStore"
    private const val KeyName = "DataKey"

    @Provides
    fun provideCreateUser(
        userManager: UserManager,
    ): CreateUser =
        CreateUser(
            userManager = userManager
        )

    @Provides
    fun provideUserManager(
        dataSource: ReactiveDataSource<User>
    ): UserManager =
        UserManager(datasource = dataSource)

    @Provides
    @Singleton
    fun provideUserDataStoreDataSource(
        dataStore: DataStore<User>
    ): ReactiveDataSource<User> =
        DataStoreDataSource(dataStore = dataStore)

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext applicationContext: Context,
        serializer: Serializer<User>
    ): DataStore<User> =
        DataStoreFactory.create(
            serializer = serializer,
            produceFile = { File(applicationContext.filesDir, "datastore/$SecuredFileName") }
        )

    @Provides
    fun provideUserSerializer(
        crypto: Crypto
    ): Serializer<User> =
        SecuredSerialized(
            crypto = crypto,
            modelSerializer = User.serializer(),
            produceDefault = { User(String()) }
        )

    @Provides
    fun provideCrypto(
        cipherProvider: CipherProvider
    ): Crypto =
        CryptoImpl(
            cipherProvider = cipherProvider
        )

    @Provides
    fun provideCipherProvider(
        keyStore: KeyStore
    ): CipherProvider =
        AesCipherProvider(
            keyStore = keyStore,
            keyStoreName = KeyStoreName,
            keyName = KeyName
        )

    @Provides
    fun provideKeyStore(): KeyStore =
        KeyStore.getInstance(KeyStoreName).apply { load(null) }
}
