package platform

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.arnyminerz.missilegame.utils.toast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

actual val platformIntroPages: List<PlatformIntroPage> = listOfNotNull(
    GenderIntroPage,
    LocationPermissionsIntroPage,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        NotificationsPermissionIntroPage
    else
        null
)

@OptIn(ExperimentalMaterial3Api::class)
object GenderIntroPage : PlatformIntroPage() {
    @Composable
    override fun ColumnScope.Content() {
        val context = LocalContext.current
        val grammaticalGender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            LocalConfiguration.current.grammaticalGender
        } else {
            GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_NOT_SPECIFIED
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            MultiChoiceSegmentedButtonRow {
                SegmentedButton(
                    checked = grammaticalGender == GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_NEUTRAL,
                    onCheckedChange = { checked ->
                        if (checked) {
                            GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                                context,
                                GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_NEUTRAL
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Text(stringResource(R.string.gender_neutral)) }
                SegmentedButton(
                    checked = grammaticalGender == GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_MASCULINE,
                    onCheckedChange = { checked ->
                        if (checked) {
                            GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                                context,
                                GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_MASCULINE
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Text(stringResource(R.string.gender_masculine)) }
                SegmentedButton(
                    checked = grammaticalGender == GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_FEMININE,
                    onCheckedChange = { checked ->
                        if (checked) {
                            GrammaticalInflectionManagerCompat.setRequestedApplicationGrammaticalGender(
                                context,
                                GrammaticalInflectionManagerCompat.GRAMMATICAL_GENDER_FEMININE
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) { Text(stringResource(R.string.gender_feminine)) }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
object LocationPermissionsIntroPage : PlatformIntroPage() {
    @Composable
    private fun rememberBackgroundLocationPermissionState() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            rememberPermissionState(
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        else
            null

    @Composable
    fun rememberLocationPermissionState(onPermissionsResult: (Map<String, Boolean>) -> Unit = {}) =
        rememberMultiplePermissionsState(
            permissions = listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            onPermissionsResult = onPermissionsResult
        )

    @Composable
    override fun canGoNext(): State<Boolean> {
        val backgroundPermission = rememberBackgroundLocationPermissionState()
        val permission = rememberLocationPermissionState()

        return produceState(
            initialValue = false,
            backgroundPermission?.status,
            permission.allPermissionsGranted
        ) {
            value = permission.allPermissionsGranted && backgroundPermission?.status?.isGranted != false
        }
    }

    @Composable
    override fun ColumnScope.Content() {
        val context = LocalContext.current

        var launchedPermissionRequest by remember { mutableStateOf(false) }
        val backgroundPermission = rememberBackgroundLocationPermissionState()
        val permission = rememberLocationPermissionState {
            if (it.values.all { granted -> granted } && backgroundPermission?.status?.isGranted == false) {
                context.toast(R.string.intro_location_grant_background)
                backgroundPermission.launchPermissionRequest()
            }
        }

        Image(
            painter = painterResource(R.drawable.undraw_my_location),
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 64.dp)
        )

        Text(
            text = stringResource(R.string.intro_location_title),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 32.dp),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.intro_location_message),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = {
                launchedPermissionRequest = true
                if (permission.allPermissionsGranted) {
                    backgroundPermission?.launchPermissionRequest()
                } else {
                    permission.launchMultiplePermissionRequest()
                }
            },
            enabled = !permission.allPermissionsGranted ||
                backgroundPermission?.status?.isGranted == false,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(
                    if (permission.allPermissionsGranted &&
                        backgroundPermission?.status?.isGranted != false
                    )
                        R.string.intro_location_granted
                    else
                        R.string.intro_location_grant
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
object NotificationsPermissionIntroPage : PlatformIntroPage() {
    @Composable
    fun rememberNotificationPermissionState() =
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

    @Composable
    override fun canGoNext(): State<Boolean> {
        val permission = rememberNotificationPermissionState()

        return produceState(false, permission.status) {
            value = permission.status.isGranted
        }
    }

    @Composable
    override fun ColumnScope.Content() {
        val permission = rememberNotificationPermissionState()

        Image(
            painter = painterResource(R.drawable.undraw_push_notifications),
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 64.dp)
        )

        Text(
            text = stringResource(R.string.intro_notifications_title),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 32.dp),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.intro_notifications_message),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = permission::launchPermissionRequest,
            enabled = !permission.status.isGranted,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                stringResource(
                    if (permission.status.isGranted)
                        R.string.intro_notifications_granted
                    else
                        R.string.intro_notifications_grant
                )
            )
        }
    }
}
