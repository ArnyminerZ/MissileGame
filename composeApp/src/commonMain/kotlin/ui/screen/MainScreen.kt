package ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi

@OptIn(ExperimentalAdaptiveApi::class)
class MainScreen : Screen {
    @Composable
    override fun Content() {
        AdaptiveScaffold(
            topBar = {
                AdaptiveTopAppBar(
                    title = { Text("Main Screen") }
                )
            }
        ) { paddingValues ->

        }
    }
}
