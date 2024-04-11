package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import auth.AuthState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.russhwolf.settings.ExperimentalSettingsApi
import storage.settings.SettingsKeys
import storage.settings.settings
import ui.screen.IntroScreen
import ui.screen.LoginScreen
import ui.screen.LoadingScreen
import ui.screen.MainScreen
import ui.theme.AppTheme

@Composable
@OptIn(ExperimentalSettingsApi::class)
fun MainComposable() {
    val isLoggedIn by AuthState.isLoggedIn().collectAsState(null)
    val introShown by settings.getBooleanFlow(SettingsKeys.INTRO_SHOWN, false).collectAsState(false)

    AppTheme {
        Navigator(LoginScreen()) { navigator ->
            LaunchedEffect(isLoggedIn, navigator) {
                when (isLoggedIn) {
                    null -> navigator.push(LoadingScreen())
                    true -> navigator.push(MainScreen())
                    false -> navigator.push(
                        if (introShown)
                            LoginScreen()
                        else
                            IntroScreen()
                    )
                }
            }

            CurrentScreen()
        }
    }
}
