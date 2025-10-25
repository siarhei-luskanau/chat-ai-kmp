package org.company.shared.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import shared.common.GenericResult
import shared.koog.KoogService

class StartViewModel(private val koogService: KoogService) : ViewModel() {

    val viewState = MutableStateFlow<StartViewState>(StartViewState.Loading)

    fun onEvent(event: StartViewEvent) {
        when (event) {
            StartViewEvent.Launched -> viewModelScope.launch {
                val result = koogService.askLlm(agentInput = "Hello! How can you help me?")
                val newViewState = when (result) {
                    is GenericResult.Failure -> StartViewState.Error(error = result.error)
                    is GenericResult.Success -> StartViewState.Success(data = result.result)
                }
                viewState.emit(newViewState)
            }
        }
    }
}
