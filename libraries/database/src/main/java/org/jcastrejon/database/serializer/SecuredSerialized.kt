package org.jcastrejon.database.serializer

import androidx.datastore.core.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.jcastrejon.database.crypto.Crypto
import java.io.InputStream
import java.io.OutputStream

class SecuredSerialized<T>(
    private val crypto: Crypto,
    private val modelSerializer: KSerializer<T>,
    produceDefault: () -> T
) : Serializer<T> {

    override val defaultValue: T = produceDefault()

    override suspend fun readFrom(input: InputStream): T =
        Json.decodeFromString(modelSerializer, crypto.decrypt(input).decodeToString())

    override suspend fun writeTo(t: T, output: OutputStream) {
        crypto.encrypt(Json.encodeToString(modelSerializer, t).encodeToByteArray(), output)
    }
}
