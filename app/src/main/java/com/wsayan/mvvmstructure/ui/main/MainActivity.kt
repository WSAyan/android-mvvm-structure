package com.wsayan.mvvmstructure.ui.main

import android.os.Bundle
import com.wsayan.mvvmstructure.databinding.ActivityMainBinding
import com.wsayan.mvvmstructure.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun viewRelatedTask() {

    }
}
