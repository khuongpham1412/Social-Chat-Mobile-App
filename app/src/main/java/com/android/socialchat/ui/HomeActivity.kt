package com.android.socialchat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.socialchat.R
import com.android.socialchat.databinding.ActivityHomeBinding
import com.android.socialchat.fragmentadapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.viewPager2.adapter = adapter
        optionFragment()
    }

    private fun optionFragment() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "HOME"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_home)
                }

                1 -> {
                    tab.text = "USER"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_me)
                }
            }
        }.attach()
    }
}