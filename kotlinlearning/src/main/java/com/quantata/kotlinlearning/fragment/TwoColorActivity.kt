package com.quantata.kotlinlearning.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quantata.kotlinlearning.R
import com.quantata.kotlinlearning.databinding.ActivityTwoColorBinding

class TwoColorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTwoColorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingButtons()
    }

    fun settingButtons() {
        binding.btnRedFragment.setOnClickListener{
            val fragmentTransaction = supportFragmentManager.beginTransaction() // Fragment Transaction 객체 생성
            fragmentTransaction.replace(R.id.FragmentFrame, RedFragment()) // Transaction 에서 무엇을 할지 정의하는 곳. 여기서는 replace!
            fragmentTransaction.commit() // transaction 이후 반드시 commit 관련 함수 호출해야 함.
        }

        binding.btnBlueFragment.setOnClickListener{
            val fragmentTransaction = supportFragmentManager.beginTransaction() // Fragment Transaction 객체 생성
            fragmentTransaction.replace(R.id.FragmentFrame, BlueFragment()) // Transaction 에서 무엇을 할지 정의하는 곳. 여기서는 replace!
            fragmentTransaction.commit() // transaction 이후 반드시 commit 관련 함수 호출해야 함.
        }
    }
}