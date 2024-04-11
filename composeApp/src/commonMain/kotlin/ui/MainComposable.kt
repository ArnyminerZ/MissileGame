package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import auth.AuthState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import ui.screen.IntroScreen
import ui.screen.LoadingScreen
import ui.theme.AppTheme

@Composable
fun MainComposable() {
    val isLoggedIn by AuthState.isLoggedIn().collectAsState(null)

    AppTheme {
        Navigator(IntroScreen()) { navigator ->
            LaunchedEffect(navigator) {
                when (isLoggedIn) {
                    null -> navigator.push(LoadingScreen())
                    true -> { TODO("Main UI still not implemented") }
                    false -> navigator.push(IntroScreen())
                }
            }

            CurrentScreen()
        }
    }
}
