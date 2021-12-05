package com.zarinpal.modules.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zarinpal.R
import com.zarinpal.adapter.ViewPager2Adapter
import com.zarinpal.databinding.ActivityMainBinding
import com.zarinpal.modules.repositories.view.RepositoriesFragment
import com.zarinpal.modules.userInfo.view.UserInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {

        // setup viewpager
        val adapter = ViewPager2Adapter(this)
        adapter.addFragment(UserInfoFragment.newInstance(), getString(R.string.user_info))
        adapter.addFragment(RepositoriesFragment.newInstance(), getString(R.string.repositories))
        binding.vpg.adapter = adapter
        binding.vpg.currentItem = 0

        TabLayoutMediator(binding.tab, binding.vpg) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}