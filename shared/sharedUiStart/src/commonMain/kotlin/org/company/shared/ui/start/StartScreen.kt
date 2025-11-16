package org.company.shared.ui.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shared.ui.common.resources.Res
import shared.ui.common.resources.Res as CommonRes
import shared.ui.common.resources.ic_dark_mode
import shared.ui.common.resources.ic_light_mode
import shared.ui.common.resources.theme
import shared.ui.common.theme.AppTheme
import shared.ui.common.theme.LocalThemeIsDark

@Preview
@Composable
fun StartScreen(viewModelProvider: () -> StartViewModel) {
    val viewModel = viewModel { viewModelProvider() }
    StartContent(
        viewState = viewModel.viewState,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun StartContent(viewState: StateFlow<StartViewState>, onEvent: (StartViewEvent) -> Unit) {
    val viewState = viewState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Start",
            style = MaterialTheme.typography.displayLarge
        )
        when (val result = viewState.value) {
            StartViewState.Loading -> Text(
                text = "Loading...",
                style = MaterialTheme.typography.displaySmall
            )

            is StartViewState.Success -> Text(
                text = result.data,
                style = MaterialTheme.typography.displaySmall
            )

            is StartViewState.Error -> Text(
                text = result.error.toString(),
                style = MaterialTheme.typography.displaySmall
            )
        }

        var isDark by LocalThemeIsDark.current
        val icon = remember(isDark) {
            if (isDark) {
                CommonRes.drawable.ic_light_mode
            } else {
                CommonRes.drawable.ic_dark_mode
            }
        }

        ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { isDark = !isDark },
            content = {
                Icon(vectorResource(icon), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(Res.string.theme))
            }
        )
    }
    LaunchedEffect(Unit) {
        onEvent(StartViewEvent.Launched)
    }
}

@Preview
@Composable
internal fun StartContentPreview() = AppTheme({}) {
    StartContent(
        viewState = MutableStateFlow(StartViewState.Success("Success")),
        onEvent = {}
    )
}
