package com.packt.domain.util

sealed class Resource<out T> {

    /**
     * Represents a loading state, typically used to show progress indicators in the UI.
     */
    object Loading : Resource<Nothing>()

    /**
     * Represents a successful result of an operation.
     *
     * @param data The data returned from the operation.
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * Represents an error state, carrying an optional exception.
     *
     * @param exception The exception that occurred during the operation, if any.
     */
    data class Error(val exception: Throwable?) : Resource<Nothing>()
}
