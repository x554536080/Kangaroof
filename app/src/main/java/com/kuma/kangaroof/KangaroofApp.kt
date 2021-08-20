package com.kuma.kangaroof

import android.app.Application
import android.content.Context

class KangaroofApp : Application(){
    companion object {
        const val WEATHER_TOKEN = "vBT3CWztClQkQmZm"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}