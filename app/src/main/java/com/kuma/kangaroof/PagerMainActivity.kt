package com.kuma.kangaroof

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.kuma.base.KumaBaseActivity
import com.kuma.kangaroof.fragment.*
import com.kuma.kangaroof.ui.NoAnimationViewPager
import kotlinx.android.synthetic.main.activity_pager_main.*

class PagerMainActivity : KumaBaseActivity() {
    lateinit var tabs: TabLayout
    lateinit var viewPager: NoAnimationViewPager
    var fragments: MutableList<Fragment> = mutableListOf()
    var titles: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_main)

        init()
    }

    private fun init() {
        fragments.add(MainFragment())
        fragments.add(SocialFragment())
        fragments.add(MessageFragment())
        fragments.add(IndividualFragment())
        titles.add("首页");
        titles.add("社区");
        titles.add("消息");
        titles.add("我的");

        viewPager = findViewById(R.id.main_vp)
        viewPager.setNoScroll(true)
        viewPager.setAnimationEnabled(false)
        tabs = findViewById(R.id.main_tabs)

//        val tabIcon: TabLayout.Tab = tabs.newTab()
//        tabIcon.text = ""
//        tabIcon.view.background = AppCompatResources
//                .getDrawable(this, R.mipmap.ic_launcher_round)
//        tabs.addTab(tabIcon)

        main_tabs.setupWithViewPager(viewPager)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }
//
//        viewPager.currentItem = 1;
//        tabs.getTabAt(1)?.select();

    }
}