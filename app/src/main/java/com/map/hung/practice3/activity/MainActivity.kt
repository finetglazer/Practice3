package com.map.hung.practice3.activity

import TimKiemFragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.map.hung.practice3.R
import com.map.hung.practice3.fragment.DanhSachFragment
import com.map.hung.practice3.fragment.ThongTinFragment


class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FAB
        fab = findViewById(R.id.fab_add_course)
        fab.setOnClickListener {
            val intent = Intent(this, AddCourseActivity::class.java)
            startActivity(intent)
        }

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        setupBottomNavigation()

        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DanhSachFragment())
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_list -> {
                    loadFragment(DanhSachFragment())
                    true
                }
                R.id.nav_info -> {
                    loadFragment(ThongTinFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(TimKiemFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Replace the current fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
