package com.example.passfort

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object DataSerializer: Serializer<Records> {
    override val defaultValue: Records = Records.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Records {
        try {
            return Records.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: Records, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.recordsDataStore: DataStore<Records> by dataStore(
        fileName = "Records.proto",
        serializer = DataSerializer
    )
}