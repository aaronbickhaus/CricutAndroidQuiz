package com.cricut.domain.usecase.abstract

import com.cricut.common.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * [UseCase]
 *
 * abstract usecase. All other usecases should extend this
 *
 */
abstract class UseCase<in Params, out T> where T : Any {
    /**
     * [invoke]
     *
     * @param params [Params]
     * @param dispatcher [CoroutineDispatcher]
     */
    suspend operator fun invoke(params: Params, dispatcher : CoroutineDispatcher): Result<T> {
        return try {
            withContext(dispatcher) {
                execute(params)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
    protected abstract suspend fun execute(params: Params): Result<T>
}