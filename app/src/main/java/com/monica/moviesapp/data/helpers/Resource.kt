package com.monica.moviesapp.data.helpers

sealed class Resource<out T> {

    data class Success<T>(val data: T? = null) : Resource<T>()
    data class Failure(val failureData: FailureData? = null) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()

}
