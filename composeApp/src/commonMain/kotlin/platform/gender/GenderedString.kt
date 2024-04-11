package platform.gender

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
data class GenderedString(
    val masculine: StringResource,
    val feminine: StringResource,
    val neutral: StringResource
)

@Composable
expect operator fun GenderedString.invoke(): String
