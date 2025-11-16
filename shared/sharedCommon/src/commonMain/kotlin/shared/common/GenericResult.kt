package shared.common

import kotlin.coroutines.cancellation.CancellationException

sealed interface GenericResult<T> {
    data class Failure<T>(val error: Throwable) : GenericResult<T>
    data class Success<T>(val result: T) : GenericResult<T>

    fun <R> map(transform: (T) -> R): GenericResult<R> = when (this) {
        is Failure -> Failure(error)

        is Success -> try {
            Success(transform(result))
        } catch (error: Throwable) {
            Failure(error)
        }
    }

    suspend fun <R> mapSuspend(transform: suspend (T) -> R): GenericResult<R> = when (this) {
        is Failure -> Failure(error)

        is Success -> try {
            Success(transform(result))
        } catch (error: CancellationException) {
            throw error
        } catch (error: Throwable) {
            Failure(error)
        }
    }
}
