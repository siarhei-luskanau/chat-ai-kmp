package shared.common

sealed interface GenericResult<T> {
    data class Failure<T>(val error: Throwable) : GenericResult<T>
    data class Success<T>(val result: T) : GenericResult<T>
}
