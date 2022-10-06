package com.authentic.aip.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: Any?) : Resource<T>(data as T?)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}