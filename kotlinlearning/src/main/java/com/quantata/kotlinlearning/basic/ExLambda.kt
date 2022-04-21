package com.quantata.kotlinlearning.basic

import android.util.Log

// 람다식은 마치 값처럼 다룰 수 있는 익명 함수이다.
class ExLambda {
    companion object {
        val TAG = ExLambda::class.java.simpleName
    }
    fun result() {
        firstLambda()
        secondLambda()
        thirdLambda()
        fourthLambda()
    }
    private fun firstLambda() {
        Log.d(TAG, "================ firstLambda ================")
        val sayHello = fun() { println("안녕하세요")}
        sayHello() // 안녕하세요.
        Log.d(TAG, "=============================================")
    }

    private fun secondLambda() {
        Log.d(TAG, "================ secondLambda ================")

        val squareNum : (Int) -> (Int) = { number -> number * number }
        println(squareNum(4)) // 16

        val squareNum2 = {number: Int -> "나의 숫자는 ${number * number}"} // Int 가 자료형 -> 반환형 아님!
        println(squareNum2(5))

        val squareNum3: (Int) -> Int = {it * it}
        println(squareNum3(6))

        val squareNum4 = {it: Int -> it * it}
        println(squareNum4(7))

        Log.d(TAG, "=============================================")

    }

    private fun thirdLambda() {
        Log.d(TAG, "================ thirdLambda ================")

        // 함수의 매개변수(인수)로 람다식 넣기
        val paramLambda : (Int) -> Boolean = {
            num -> num == 10
        }
        println(invokeLambda(paramLambda)) // 5 != 10 이므로 false
        Log.d(TAG, "=============================================")

    }

    private fun invokeLambda(lambda: (Int) -> Boolean) : Boolean {
        return lambda(4)
    }

    private fun fourthLambda() {
        Log.d(TAG, "================ fourthLambda ================")

        println("첫번째 = ${invokeLambda({num -> num == 10})})")
        println("두번째 = ${invokeLambda({it == 10})})")
        println("세번째 = ${ invokeLambda() {it == 4}})")
        println("네번째 = ${ invokeLambda{ it == 10 }})")

        invokeLambda({num -> num == 10}) // 람다식 바로 넣어주기
        invokeLambda({it == 10}) // 인수가 하나일 때 it 으로 변경 가능
        invokeLambda() {it == 10} // 만약 함수의 마지막 인수가 람다일 겨우 밖으로 뺄 수 있음
        invokeLambda { it == 10 } // 그 외 인수가 없을 때 () 생략 가능
        Log.d(TAG, "=============================================")
    }
}