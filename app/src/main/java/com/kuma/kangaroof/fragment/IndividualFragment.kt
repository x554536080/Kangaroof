package com.kuma.kangaroof.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kuma.kangaroof.Kangaroof
import com.kuma.kangaroof.LoginActivity
import com.kuma.kangaroof.databinding.FragmentIndividualBinding
import com.kuma.base.util.UIUtil


class IndividualFragment : Fragment() {
    private var binding: FragmentIndividualBinding? = null

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
        UIUtil.setStatusBarImmersive(activity?.window!!)

        if (Kangaroof.isLogin) {
            binding?.fragmentIndividualLoginStatusTv?.text = "user name"
            binding?.fragmentIndividualLoginIv?.setOnClickListener {
                Toast.makeText(
                    activity,
                    "即将开启修改头像功能",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            binding?.fragmentIndividualLoginStatusTv?.text = "未登录"
            binding?.fragmentIndividualLoginRl?.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

    }
}