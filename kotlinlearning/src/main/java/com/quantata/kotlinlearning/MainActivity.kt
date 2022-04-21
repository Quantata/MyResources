package com.quantata.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quantata.kotlinlearning.basic.*
import com.quantata.kotlinlearning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Basic Kotlin
        binding.btnGoBasic.setOnClickListener{
            startActivity(Intent(this, BasicActivity::class.java))
        }


    }
}