package com.brianperin.githubrepository.network

import com.apollographql.apollo.api.Response
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 * so our view models can display loading/complete/error to the view
 */
abstract class BaseDataSource {

    protected suspend fun <T> getQueryResult(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.hasErrors()) {
                Result.error(response.errors.toString())
            } else {
                Result.success(response.data!!)
            }
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.error(message)
    }

}

