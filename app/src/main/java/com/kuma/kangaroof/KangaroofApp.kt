package com.kuma.kangaroof

import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob

class KangaroofApp : Application(){
    companion object {
        const val WEATHER_TOKEN = "vBT3CWztClQkQmZm"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Bmob.initialize(this, "a89262be4f589b9e7eec888d0ec4903d")
    }
}