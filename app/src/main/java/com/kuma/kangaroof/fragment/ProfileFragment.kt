package com.kuma.kangaroof.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.kuma.kangaroof.Kangaroof
import com.kuma.kangaroof.LoginActivity
import com.kuma.kangaroof.databinding.FragmentIndividualBinding
import com.kuma.base.util.UIUtil


class ProfileFragment : Fragment() {
    private var binding: FragmentIndividualBinding? = null

    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIndividualBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        UIUtil.setStatusBarImmersive(activity?.window!!)


        refreshLoginStatus()

//        if (Kangaroof.isLogin) {
//            binding?.fragmentIndividualLoginStatusTv?.text = "user name"
//            binding?.fragmentIndividualLoginIv?.setOnClickListener {
//                Toast.makeText(
//                    activity,
//                    "即将开启修改头像功能",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        } else {
//            binding?.fragmentIndividualLoginRl?.setOnClickListener {
//                startActivityForResult(Intent(activity, LoginActivity::class.java), 1)
//            }
//        }
    }

    private fun initData() {
        token = SPUtils.getInstance()?.getString("user_token") ?: ""
        Kangaroof.isLogin = !StringUtils.isEmpty(token)
    }

    private fun refreshLoginStatus() {
        initData()
        binding?.fragmentIndividualLoginStatusTv?.text = if (!Kangaroof.isLogin) {
            "未登录"
        } else {
            "退出登录"
        }
        binding?.fragmentIndividualLoginRl?.setOnClickListener {
            if (Kangaroof.isLogin) {
                SPUtils.getInstance()?.put("user_token", "")
                Kangaroof.isLogin = false
                refreshLoginStatus()
            } else {
                startActivityForResult(Intent(activity, LoginActivity::class.java), 1)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        refreshLoginStatus()
    }
}