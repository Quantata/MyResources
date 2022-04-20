package com.quantata.kotlinlearning.basic

class BasicDataType {

    companion object {
        fun result() {
            /** 기본 자료형 **/
            // 배열 자료형
            val stringArray : Array<String> = arrayOf("apple", "banana", "grape")
            val intArray = arrayOf(1,2,3) // 자료형 생략

            println(stringArray[0])
            println(intArray[2])

            // 명시적 형변환
            val score = 100 // Int형
            val scoreString = score.toString() // String 형
            val scoreDouble = score.toDouble() // Double 형

            println(scoreDouble) // 100.0
        }
    }
}