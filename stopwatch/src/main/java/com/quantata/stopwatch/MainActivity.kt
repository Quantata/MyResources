package com.quantata.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.quantata.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener { // 클릭 이벤트 처리 인터페이스 = OnClickListener
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> {
                Toast.makeText(this, "g2", Toast.LENGTH_SHORT).show()
            }
        }
    }


}