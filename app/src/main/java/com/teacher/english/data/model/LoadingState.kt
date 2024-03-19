package com.teacher.english.data.model

import java.io.PrintWriter
import java.io.StringWriter

sealed class LoadingState<out T>(val data: T?) {

    abstract fun <R> copy(newData: R? = null): LoadingState<R>

    class Error<out T>(val throwable: Throwable, data: T?) : LoadingState<T>(data) {
        fun getStackTrace(): String {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            return stringWriter.toString()
        }

        override fun <R> copy(newData: R?) =
            Error<R>(throwable, newData)
    }

    class Loading<out T>(data: T?) : LoadingState<T>(data) {
        override fun <R> copy(newData: R?) =
            Loading<R>(newData)
    }

    class Success<out T>(data: T?) : LoadingState<T>(data) {
        override fun <R> copy(newData: R?) =
            Success<R>(newData)
    }

    companion object {
        fun <T> loading(data: T? = null) = Loading(data)

        fun <T> success(data: T?) = Success(data)

        fun <T> error(throwable: Throwable, data: T? = null) =
            Error(throwable, data)
    }

    override fun toString(): String {
        return "${this::class.simpleName} (${
            if (this is Error) "${throwable::class.simpleName}, " else ""
        }$data)"
    }
}

fun <T, R> LoadingState<T>.map(convertor: LoadingState<T>.(T?) -> R?): LoadingState<R> {
    val conversion = convertor(data)
    return when (this) {
        is LoadingState.Error -> LoadingState.error(throwable, conversion)
        is LoadingState.Loading -> LoadingState.loading(conversion)
        is LoadingState.Success -> LoadingState.success(conversion)
    }
}