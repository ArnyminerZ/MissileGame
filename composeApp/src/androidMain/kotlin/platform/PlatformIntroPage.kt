package platform

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.GrammaticalInflectionManagerCompat
import com.arnyminerz.missilegame.R
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi

actual val platformIntroPages: List<PlatformIntroPage> = listOf(
    GenderIntroPage,
    PermissionsIntroPage
)

@OptIn(ExperimentalAdaptiveApi::class)
object GenderIntroPage : PlatformIntroPage {
    @Composable
    override fun ColumnScope.Content() {
        val context = LocalContext.current
        val grammaticalGender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            LocalConfiguration.current.grammaticalGender
        } else {
            0
        }

        Text(
            text = stringResource(R.string.intro_gender_title),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 64.dp),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.intro_gender_message),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        AnimatedContent(
            targetState = grammaticalGender,
            label = "animate gender changes",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { gender ->
            Icon(
                painter = painterResource(
                    when (gender) {
                        // Feminine
                        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_FEMININE -> R.drawable.gender_female
                        // Masculine
                        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_MASCULINE -> R.drawable.gender_male
                        // Neuter, unspecified
                        else -> R.drawable.gender_nonbinary
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(96.dp).padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            AdaptiveButton(
                onClick = {
                    GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                        context,
                        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_NEUTRAL
                    )
                },
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) { Text(stringResource(R.string.gender_neutral)) }
            AdaptiveButton(
                onClick = {
                    GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                        context,
                        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_MASCULINE
                    )
                },
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) { Text(stringResource(R.string.gender_masculine)) }
            AdaptiveButton(
                onClick = {
                    GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                        context,
                        GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_FEMININE
                    )
                },
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) { Text(stringResource(R.string.gender_feminine)) }
        }
    }
}

object PermissionsIntroPage : PlatformIntroPage {
    @Composable
    override fun ColumnScope.Content() {
        // TODO
        Text("Permissions")
    }
}
