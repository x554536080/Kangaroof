package com.kuma.kangaroof

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import java.lang.ref.WeakReference


class SplashActivity : AppCompatActivity() {

    private class MyHandler(activity: SplashActivity?) : Handler() {
        //持有弱引用HandlerActivity，GC回收时会被回收掉
        private val mActivity: WeakReference<SplashActivity> = WeakReference<SplashActivity>(activity)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val activity: SplashActivity? = mActivity.get()
            activity?.startMainActivity()
        }
    }

    private val handler = MyHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        splash_logo.setOnClickListener {
//            startMainActivity()
//        }
        PermissionX.init(this).permissions(Manifest.permission.CAMERA)
                .request { allGranted, p1, p2 ->
                    if (allGranted) {
                        handler.sendEmptyMessageDelayed(0, 1200)
                    } else {
                        finish()
                    }
                }

    }


    /**
     * 跳转主界面
     */
    fun startMainActivity() {
        startActivity(Intent(this, PagerMainActivity::class.java))
        finish()
    }

}