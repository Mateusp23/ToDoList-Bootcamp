package com.example.todo_bootcamp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_bootcamp.R
import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    private val time: Int = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            val i = Intent(
                this@SplashActivity, MainActivity::class.java
            )
            startActivity(i)
            finish()
        }, time.toLong())
    }
}