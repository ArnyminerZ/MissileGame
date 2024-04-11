package platform.gender

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.GrammaticalInflectionManagerCompat
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
@OptIn(ExperimentalResourceApi::class)
actual operator fun GenderedString.invoke(): String {
    val context = LocalContext.current
    val gender = GrammaticalInflectionManagerCompat.getApplicationGrammaticalGender(context)
    return when (gender) {
        Configuration.GRAMMATICAL_GENDER_MASCULINE -> stringResource(masculine)
        Configuration.GRAMMATICAL_GENDER_FEMININE -> stringResource(feminine)
        else -> stringResource(neutral)
    }
}
