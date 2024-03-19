package com.teacher.english.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class GsonDateFormatAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {
    private val dateFormat = ISO_8601_DATE_FORMAT.apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val dateFormat2 = ISO_8601_DATE_FORMAT.apply { timeZone = TimeZone.getTimeZone("UTC") }
    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return try {
            JsonPrimitive(dateFormat.format(src))
        } catch (e: Exception) {
            JsonPrimitive(dateFormat2.format(src))
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Date {
        return try {
            dateFormat.parse(json?.asString)
        } catch (e: ParseException) {
            try {
                dateFormat2.parse(json?.asString)
            } catch (e: ParseException) {
                throw JsonParseException(e)
            }
        }
    }
}

val ISO_8601_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
val ISO_8601_EXTENDED_HMS_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
