package com.overlaydemo.utils.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.overlaydemo.R
import com.overlaydemo.databinding.ActivityVerticalViewPagerBinding

class VerticalViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerticalViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerticalViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        val list = ArrayList<String>()
        list.add("3GB")
        list.add("10GB")
        list.add("15GB")
        list.add("8GB")
        list.add("33GB")

        binding.customViewPager.setDataList(list)

    }


}