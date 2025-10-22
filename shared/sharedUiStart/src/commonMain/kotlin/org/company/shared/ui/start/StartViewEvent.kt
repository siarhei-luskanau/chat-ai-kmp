package org.company.shared.ui.start

sealed interface StartViewEvent {
    data object Launched : StartViewEvent
}
