package com.alienspace.weatherspace.utils

sealed class Results<T>() {

    data class Success<T>(val data: T) : Results<T>()
    data class Error<T>(val errorType: ErrorType) : Results<T>()
}

enum class ErrorType {
    CLIENT,
    SERVER,
    GENERIC,
    IO_CONNECTION,
    UNAUTHORIZED
}