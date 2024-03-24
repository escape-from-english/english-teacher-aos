package com.teacher.english.data.datastore

import java.io.InputStream
import java.io.OutputStream

interface DataStoreEncryption {
    fun encrypt(bytes: ByteArray, outputStream: OutputStream): ByteArray
    fun decrypt(inputStream: InputStream): ByteArray
}
