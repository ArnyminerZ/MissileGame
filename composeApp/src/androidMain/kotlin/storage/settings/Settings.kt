package storage.settings

import com.arnyminerz.missilegame.appContext
import com.arnyminerz.missilegame.dataStore
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.datastore.DataStoreSettings

@OptIn(
    ExperimentalSettingsApi::class,
    ExperimentalSettingsImplementation::class
)
actual val settings: FlowSettings by lazy {
    DataStoreSettings(appContext.dataStore)
}
