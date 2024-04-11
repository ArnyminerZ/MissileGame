package platform

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable

expect val platformIntroPages: List<PlatformIntroPage>

interface PlatformIntroPage {
    @Composable
    fun ColumnScope.Content()
}
