package com.quantata.kotlinlearning.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quantata.kotlinlearning.R

class BasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        // 1. Basic
//        BasicDataType.result()
//        BasicFunction.result()
//        BasicControlClass.result()
//        BasicCollection.result()

        // 2. Class - Lambda
        ExLambda().result()
    }
}