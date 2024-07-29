package com.kuma.kangaroof

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Process
import android.widget.Toast
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class KangaroofApp : Application() {
    companion object {
        const val WEATHER_TOKEN = "vBT3CWztClQkQmZm"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            val sw = StringWriter()
            val pr = PrintWriter(sw)
            e.printStackTrace(pr)
            pr.close()
            val crashText = sw.toString()

            val path = "${externalMediaDirs[0].absolutePath}/crash.txt"
            val fw = FileWriter(File(path), false)//直接不加这个参数？
            fw.write(crashText)
            fw.close()

            Toast.makeText(context, "程序出现异常，即将退出...", Toast.LENGTH_SHORT).show()
            Thread.sleep(2000)
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext


//        Bmob.initialize(this, "a89262be4f589b9e7eec888d0ec4903d")
//        UserUsingInfoUtil.saveLaunchInfo(context)

    }
}