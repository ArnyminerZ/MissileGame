package platform

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

expect val platformIntroPages: List<PlatformIntroPage>

abstract class PlatformIntroPage {
    @Composable
    open fun canGoNext(): State<Boolean> = derivedStateOf { true }

    @Composable
    abstract fun ColumnScope.Content()
}
