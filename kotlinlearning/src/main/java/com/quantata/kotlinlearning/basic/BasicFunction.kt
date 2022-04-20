package com.quantata.kotlinlearning.basic

class BasicFunction {
    companion object {
        fun result() {
            // 반환값이 있는 함수
            fun addNum(a: Int, b: Int) : Int {
                return a + b
            }

            println(addNum(2,3))

            // 단일 표현식 함수 = 실행할 코드가 표현식 하나로 이루어진 함수
            fun minusNum(a: Int, b: Int) = a - b

            println(minusNum(minusNum(100, 30), 10))
        }
    }
}