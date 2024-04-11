package com.arnyminerz.missilegame

import android.app.Application

lateinit var appContext: AppBase

class AppBase : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
