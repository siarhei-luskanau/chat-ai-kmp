package org.company.shared.ui.start

sealed interface StartViewState {
    data object Loading : StartViewState
    data class Success(val data: String) : StartViewState
    data class Error(val error: Throwable) : StartViewState
}
