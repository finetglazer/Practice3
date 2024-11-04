// ViewPagerAdapter.kt
package com.map.hung.practice3.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.map.hung.practice3.activity.MainActivity
import com.map.hung.practice3.fragment.CovidListFragment
import com.map.hung.practice3.fragment.InformationFragment
import com.map.hung.practice3.fragment.SearchStatsFragment

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CovidListFragment()
            1 -> InformationFragment()
            2 -> SearchStatsFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
