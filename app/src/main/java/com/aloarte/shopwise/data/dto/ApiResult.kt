package com.aloarte.shopwise.data.dto

sealed class ApiResult<T> {

    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error<T>(
        val errorCode: Int,
        val errorMessage: String? = null,
        val exception: Exception? = null
    ) : ApiResult<T>()
}
