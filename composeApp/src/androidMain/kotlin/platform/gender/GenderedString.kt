package platform.gender

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.app.GrammaticalInflectionManagerCompat
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
@OptIn(ExperimentalResourceApi::class)
actual operator fun GenderedString.invoke(): String {
    val grammaticalGender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        LocalConfiguration.current.grammaticalGender
    } else {
        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_NOT_SPECIFIED
    }
    return when (grammaticalGender) {
        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_MASCULINE -> stringResource(masculine)
        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_FEMININE -> stringResource(feminine)
        else -> stringResource(neutral)
    }
}
