package com.eugenejavinas.randomuser.common.utils

/**
 * Utility wrapper class for handling different resource fetching states.
 */
sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: Throwable): Resource<T>()
    class Loading<T>: Resource<T>()
}