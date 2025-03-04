package com.example.onboardx.utils

sealed class ResourceWrapper<out T>{
    data class Success<T>(val data : T) : ResourceWrapper<T>()
    data class Error(val message: String, val errorCode: Int? = null) : ResourceWrapper<Nothing>()
    data class Failure(val exception : Throwable) : ResourceWrapper<Nothing>()
    data object Loading : ResourceWrapper<Nothing>()
}