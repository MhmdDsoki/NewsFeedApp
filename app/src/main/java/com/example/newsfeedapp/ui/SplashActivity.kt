package com.example.newsfeedapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.newsfeedapp.common.show
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.view.animation.AnimationUtils
import com.example.newsfeedapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            whenStarted {
                delay(500)
                news_text_view.show()
                val animation: Animation =
                    AnimationUtils.loadAnimation(this@SplashActivity, R.anim.topnews_text_view)
                news_text_view.animation = animation
            }
            whenStarted {
                delay(1000)
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
