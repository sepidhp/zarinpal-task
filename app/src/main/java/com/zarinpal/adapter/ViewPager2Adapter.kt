package com.zarinpal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class ViewPager2Adapter : FragmentStateAdapter {

    private var fragments: MutableList<Fragment>
    private var titles: MutableList<String>

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {
        fragments = ArrayList()
        titles = ArrayList()
    }

    constructor(fragment: Fragment) : super(fragment) {
        fragments = ArrayList()
        titles = ArrayList()
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun addFragment(index: Int, fragment: Fragment, title: String) {
        fragments.add(index, fragment)
        titles.add(title)
    }

    fun getPageTitle(position: Int): String {
        return titles[position]
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}