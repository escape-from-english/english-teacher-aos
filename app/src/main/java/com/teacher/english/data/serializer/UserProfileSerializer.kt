package com.teacher.english.data.serializer

import androidx.datastore.core.Serializer
import com.teacher.english.data.datastore.DataStoreEncryption
import com.teacher.english.data.model.UserProfile
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserProfileSerializer(private val encryption: DataStoreEncryption) : Serializer<UserProfile> {
    override val defaultValue: UserProfile
        get() = UserProfile()

    override suspend fun readFrom(input: InputStream): UserProfile {
        val decryptedBytes = encryption.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserProfile.serializer(),
                string = decryptedBytes.decodeToString(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(profile: UserProfile, output: OutputStream) {
        encryption.encrypt(
            bytes = Json.encodeToString(
                serializer = UserProfile.serializer(),
                value = profile,
            ).encodeToByteArray(),
            outputStream = output,
        )
    }
}