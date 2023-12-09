package com.kuma.kangaroof.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.kuma.kangaroof.LeafActivity
import com.kuma.kangaroof.PlayerActivity
import com.kuma.kangaroof.R
import com.kuma.kangaroof.business.weather.ui.weather.WeatherActivity


/**
 * name: MainFragment
 * author: 熊熊熊
 * date: 2021/11/8 14:25
 * description:
 */
class MainFragment : Fragment() {
    private var drawerOpened = true

    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var button4: Button? = null

    private var mTitle: TextView? = null
    private var mAppBarLayout: AppBarLayout? = null
    private var mToolbar: Toolbar? = null
//    private var mSelfHeight: Float = 0f


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //生成对应主题的ContextThemeWrapper
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.TransparentStatusBarTheme)
        //用contextThemeWrapper生成一个新的LayoutInflater
        val cloneInContext = inflater.cloneInContext(contextThemeWrapper)
        //再用新生成的LayoutInflater实例化view
        return cloneInContext.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button1 = getView()?.findViewById(R.id.main_button_1)
        button2 = getView()?.findViewById(R.id.main_button_2)
        button3 = getView()?.findViewById(R.id.main_button_3)
        button4 = getView()?.findViewById(R.id.main_button_4)
        button1!!.setOnClickListener(onClickListener)
        button2!!.setOnClickListener(onClickListener)
        button3!!.setOnClickListener(onClickListener)
        button4!!.setOnClickListener(onClickListener)

        mTitle = getView()?.findViewById(R.id.main_title)
        mAppBarLayout = requireView().findViewById(R.id.main_appBar)
        mToolbar = requireView().findViewById(R.id.main_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        //
        setHasOptionsMenu(true)

        if (getView() != null) {
            val initHeight = resources.getDimension(R.dimen.app_bar_height)
            val toolbarHeight = resources.getDimension(R.dimen.tool_bar_height)

            mAppBarLayout?.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
//                if (mSelfHeight == 0f) {
//                    mSelfHeight = mTitle?.height
//                    val distanceTitle: Float = mTitle.getTop() + (mSelfHeight - toolbarHeight) / 2.0f
//                    mTitleScale = distanceTitle / (initHeight - toolbarHeight)
//                }
                val scale: Float = 1.0f - (-verticalOffset) / (initHeight - toolbarHeight)
                mTitle?.alpha = 1 - scale

            })
        }
    }

    private val onClickListener = View.OnClickListener { v: View ->
        if (v === button1) {
            startActivity(Intent(activity, WeatherActivity::class.java))
        }
        if (v === button2) {
            startActivity(Intent(activity, LeafActivity::class.java))
        }
        if (v === button3) {
//            currentPhotoPath = CameraUtils.takePhoto(saveToLocal, this)
        }
        if (v === button4) {
            startActivity(Intent(activity, PlayerActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerOpened = if (drawerOpened) {
                mAppBarLayout?.setExpanded(false)
                false
            } else {
                mAppBarLayout?.setExpanded(true)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}