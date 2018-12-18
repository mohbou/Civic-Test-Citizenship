package com.mohbou.enhancedtestcivic.features.home.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.features.home.fragment.QuestionListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragment = QuestionListFragment.newInstance("", "")
        fragmentManager.beginTransaction().add(R.id.homeContainer, fragment).commit()
    }
}
