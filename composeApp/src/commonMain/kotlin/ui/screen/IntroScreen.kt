package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalFoundationApi::class)
class IntroScreen : Screen {
    @Composable
    override fun Content() {
        AdaptiveScaffold { paddingValues ->
            HorizontalPager(
                state = rememberPagerState(initialPage = 1) { 3 },
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) { page ->
                when (page) {
                    0 -> RegisterPage()
                    1 -> LoginPage()
                    2 -> LostPasswordPage()
                }
            }
        }
    }

    @Composable
    private fun RegisterPage() {
        // TODO
        Text("Register")
    }

    @Composable
    private fun LoginPage() {
        Text("Login")
    }

    @Composable
    private fun LostPasswordPage() {
        // TODO
        Text("Lost Password")
    }
}
