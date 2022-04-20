package com.quantata.kotlinlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quantata.kotlinlearning.basic.BasicCollection
import com.quantata.kotlinlearning.basic.BasicControlClass
import com.quantata.kotlinlearning.basic.BasicDataType
import com.quantata.kotlinlearning.basic.BasicFunction
import com.quantata.kotlinlearning.lambda.ExLambda

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Basic
//        BasicDataType.result()
//        BasicFunction.result()
//        BasicControlClass.result()
//        BasicCollection.result()

        // 2. Class - Lambda
        ExLambda().result()
    }
}