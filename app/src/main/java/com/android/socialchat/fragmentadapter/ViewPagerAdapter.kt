package com.android.socialchat.fragmentadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.socialchat.ui.NewFeedsFragment
import com.android.socialchat.ui.UserFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(supportFragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewFeedsFragment()
            }

            1 -> {
                UserFragment()
            }

            else -> {
                Fragment()
            }
        }
    }
}