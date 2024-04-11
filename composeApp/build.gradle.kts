plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)

            // Compose - Accompanist
            implementation(libs.compose.accompanist.permissions)

            // Compose - Cupertino
            implementation(libs.compose.cupertino.adaptive)

            // Compose - Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)

            // Multiplatform Settings
            implementation(libs.multiplatformSettings.base)
            implementation(libs.multiplatformSettings.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.datastore)
            implementation(libs.compose.ui.tooling.preview)

            // Firebase
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.crashlytics)

            // Multiplatform Settings - Datastore
            implementation(libs.multiplatformSettings.datastore)
        }
    }
}

android {
    namespace = "com.arnyminerz.missilegame"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.arnyminerz.missilegame"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
