package com.example.zachet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class Activity_first : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        if (savedInstanceState == null) {
            navigateToFragment(Fragment_Search())
        }
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack(null)
        }
    }
}