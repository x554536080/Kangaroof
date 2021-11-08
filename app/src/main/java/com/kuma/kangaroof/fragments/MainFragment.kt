package com.kuma.kangaroof.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.kuma.kangaroof.R

/**
 * name: MainFragment
 * author: 熊熊熊
 * date: 2021/11/8 14:25
 * description:
 */
class MainFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //生成对应主题的ContextThemeWrapper
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.TransparentStatusBarTheme)
        //用contextThemeWrapper生成一个新的LayoutInflater
        val cloneInContext = inflater.cloneInContext(contextThemeWrapper)
        //再用新生成的LayoutInflater实例化view
        return cloneInContext.inflate(R.layout.fragment_main,container,false)
    }
}