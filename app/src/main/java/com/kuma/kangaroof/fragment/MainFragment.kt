package com.kuma.kangaroof.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.kuma.base.util.CameraUtils
import com.kuma.kangaroof.LeafActivity
import com.kuma.kangaroof.PlayerActivity
import com.kuma.kangaroof.R
import com.kuma.kangaroof.ScrollViewRecyclerViewTestActivity
import com.kuma.kangaroof.business.weather.WeatherMainActivity
import com.kuma.kangaroof.business.weather.ui.weather.WeatherActivity
import com.kuma.kangaroof.databinding.FragmentIndividualBinding
import com.kuma.kangaroof.databinding.FragmentMainBinding
import com.kuma.kangaroof.test.TestIndexActivity


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
    var button5: Button? = null

    private var mTitle: TextView? = null
    private var mAppBarLayout: AppBarLayout? = null
    private var mToolbar: Toolbar? = null
//    private var mSelfHeight: Float = 0f

    private lateinit var mBinding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        //生成对应主题的ContextThemeWrapper
//        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.TransparentStatusBarTheme)
//        //用contextThemeWrapper生成一个新的LayoutInflater
//        val cloneInContext = inflater.cloneInContext(contextThemeWrapper)
//        //再用新生成的LayoutInflater实例化view
//        return cloneInContext.inflate(R.layout.fragment_main, container, false)
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button1 = getView()?.findViewById(R.id.main_button_1)
        button2 = getView()?.findViewById(R.id.main_button_2)
        button3 = getView()?.findViewById(R.id.main_button_3)
        button4 = getView()?.findViewById(R.id.main_button_4)
        button5 = getView()?.findViewById(R.id.main_button_5)
        button1!!.setOnClickListener(onClickListener)
        button2!!.setOnClickListener(onClickListener)
        button3!!.setOnClickListener(onClickListener)
        button4!!.setOnClickListener(onClickListener)
        button5!!.setOnClickListener(onClickListener)

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
        mBinding.mainRefresh.apply {
            autoRefresh()
            setEnableRefresh(true)
            setEnableLoadMore(false)
            setOnRefreshListener {
                mBinding.mainRefresh.finishRefresh(2000)
            }
        }

        val adapter = MyAdapter()
        mBinding.mainRv.layoutManager = GridLayoutManager(activity, 2)
        mBinding.mainRv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    class MyAdapter : RecyclerView.Adapter<MyVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
            val bt = Button(parent.context)
            bt.width = 20
            bt.height = 240
            return MyVH(bt)
        }

        override fun onBindViewHolder(holder: MyVH, position: Int) {
            (holder.bt as Button).text = "233"
        }

        override fun getItemCount(): Int {
            return 20
        }
    }

    class MyVH(view: View) : RecyclerView.ViewHolder(view) {
val bt = view

    }

    private val onClickListener = View.OnClickListener { v: View ->
        if (v === button1) {
            startActivity(Intent(activity, WeatherMainActivity::class.java))
        }
        if (v === button2) {
//            start 穿搭知识库
        }
        if (v === button3) {
            startActivity(Intent(activity, LeafActivity::class.java))
//            startActivity(Intent(activity, ScrollViewRecyclerViewTestActivity::class.java))

//            currentPhotoPath = CameraUtils.takePhoto(saveToLocal, this)
        }
        if (v === button4) {
            //更换背景
        }
        if (v === button5) {
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