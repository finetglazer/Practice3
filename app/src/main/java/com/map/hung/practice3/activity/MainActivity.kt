package com.map.hung.practice3.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import com.map.hung.practice3.R
import com.map.hung.practice3.adapter.ViewPagerAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: com.google.android.material.tabs.TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab_add_covid_strain)
        fab.setOnClickListener {
            val intent = Intent(this, AddCovidStrainActivity::class.java)
            startActivity(intent)
        }

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "List"
                1 -> "Information"
                2 -> "Search & Stats"
                else -> null
            }
        }.attach()
    }
}
