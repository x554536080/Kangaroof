package com.kuma.kangaroof.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import cn.bmob.v3.Bmob.getApplicationContext
import com.kuma.kangaroof.R
import widget.sun.floatwindow.basefloat.FloatWindowParamManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


        val floatWindowPreference: SwitchPreferenceCompat? = findPreference("float_window")
        floatWindowPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (PreferenceManager.getDefaultSharedPreferences(context)
                            .getBoolean("float_window", false)) {
                val permission = FloatWindowParamManager.checkPermission(getApplicationContext())

                if (permission) {
                } else {                //先检查悬浮窗权限然后再打开悬浮窗
                    FloatWindowParamManager.tryJumpToPermissionPage(getApplicationContext())
                }

//                val intent = Intent(getApplicationContext(), FloatWindowService::class.java)
//                intent.action = FloatWindowService.ACTION_CHECK_PERMISSION_AND_TRY_ADD
//                activity?.startService(intent)
            } else {
                //TODO 关闭悬浮窗
            }
            true
        }


    }

}