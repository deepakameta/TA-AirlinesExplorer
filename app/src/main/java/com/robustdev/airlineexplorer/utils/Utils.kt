package com.robustdev.airlineexplorer.utils

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int = 0,
        val errorMsg: String = "",
    ) : Resource<Nothing>()
}