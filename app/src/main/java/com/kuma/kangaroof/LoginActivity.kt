package com.kuma.kangaroof

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.blankj.utilcode.util.SPUtils
import com.kuma.base.KumaBaseActivity
import com.kuma.kangaroof.model.User
import com.kuma.kangaroof.retrofit.manager.ApiManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginActivity : KumaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        //CameraConfigurationUtils.setFocus()
    }


    private fun initViews() {
        //edittext
        val acEdit = findViewById<EditText>(R.id.activity_login_account_et)
        val pwdEdit = findViewById<EditText>(R.id.activity_login_password_et)
        val ss1 = SpannableString("请输入用户名")
        val ss2 = SpannableString("请输入密码")
        ss1.setSpan(
            ForegroundColorSpan(Color.parseColor("#EEEEEE")),
            0, 6, SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss2.setSpan(
            ForegroundColorSpan(Color.parseColor("#EEEEEE")),
            0, 5, SPAN_EXCLUSIVE_EXCLUSIVE
        )
        acEdit.hint = SpannableString(ss1)
        pwdEdit.hint = SpannableString(ss2)
        //button
        val loginButton = findViewById<Button>(R.id.ac_login_login_bt)
        loginButton.setOnClickListener {
            ApiManager.api.login(acEdit.text.toString(), pwdEdit.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<User>() {
                    override fun onCompleted() {
                        val a = 1
                    }

                    override fun onError(e: Throwable?) {
                        val a = 1

                    }

                    override fun onNext(user: User?) {
                        SPUtils.getInstance().put("user_token", user?.token)
                        finish()
                    }

                })
//            OkhttpUtil.okHttpGet("",CallBackUtil)
        }
        //
        val closeButton = findViewById<ImageView>(R.id.activity_login_finish)
        closeButton.setOnClickListener { finish() }
    }


}