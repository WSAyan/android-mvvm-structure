package com.wsayan.mvvmstructure.ui.splash

import android.content.Intent
import android.os.Bundle
import com.wsayan.mvvmstructure.databinding.ActivitySplashBinding
import com.wsayan.mvvmstructure.ui.base.BaseActivity
import com.wsayan.mvvmstructure.ui.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun viewRelatedTask() {
        Timer("SettingUp", false).schedule(3000) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
