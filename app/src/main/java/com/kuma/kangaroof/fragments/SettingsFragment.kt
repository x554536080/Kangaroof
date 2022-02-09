package com.kuma.kangaroof.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import cn.bmob.v3.Bmob.getApplicationContext
import com.kuma.kangaroof.R
import widget.sun.floatwindow.basefloat.FloatWindowParamManager

class SettingsFragment : PreferenceFragmentCompat() {

    var mWindowManager: WindowManager? = null
    var mLayoutParams: WindowManager.LayoutParams? = null
    var mInflate: View? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        mWindowManager = context?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager


        val floatWindowPreference: SwitchPreferenceCompat? = findPreference("float_window")
        floatWindowPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (PreferenceManager.getDefaultSharedPreferences(requireContext())
                            .getBoolean("float_window", false)) {
                val permission = FloatWindowParamManager.checkPermission(getApplicationContext())
                if (!permission) {
                    //先检查悬浮窗权限然后再打开悬浮窗
                    FloatWindowParamManager.tryJumpToPermissionPage(getApplicationContext())
                }
                mInflate = View.inflate(context, R.layout.main_layout_float_kuma, null)
                mInflate?.setOnTouchListener(mTouchListener)
                mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(false, true)
                mWindowManager!!.addView(mInflate, mLayoutParams)
            } else {
                //关闭悬浮窗
                if (mWindowManager != null && mInflate != null)
                    mWindowManager!!.removeView(mInflate)
            }
            true
        }

    }


    private val mTouchListener = object : OnTouchListener {
        private var mLastY = 0f
        private var mLastX = 0f
        private var mDownY = 0f
        private var mDownX = 0f
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val x = event.rawX
            val y = event.rawY
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = x
                    mDownY = y
                    mLastX = x
                    mLastY = y
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveX = x - mLastX
                    val moveY = y - mLastY
                    Log.e("TAG", "$moveX $moveY")
                    mLayoutParams!!.x += moveX.toInt()
                    mLayoutParams!!.y += moveY.toInt()
                    mWindowManager?.updateViewLayout(mInflate, mLayoutParams)
                    mLastX = x
                    mLastY = y
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            return false
        }
    }
}