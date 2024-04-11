package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import platform.getThemeForPlatform

@Composable
@OptIn(ExperimentalAdaptiveApi::class)
fun AppTheme(
    theme: Theme = getThemeForPlatform(),
    content: @Composable () -> Unit
) {
    AdaptiveTheme(
        material = { MaterialTheme(content = it) },
        cupertino = { CupertinoTheme(content = it) },
        target = theme,
        content = content
    )
}
