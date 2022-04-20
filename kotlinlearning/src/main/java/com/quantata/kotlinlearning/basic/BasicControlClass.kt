package com.quantata.kotlinlearning.basic

class BasicControlClass {
    companion object {
        fun result() {
            rangeClass()
            forLoop()
            whileLoop()
            ifs()
            whens()
        }

        private fun rangeClass() {
            //범위 클래스: IntRagne, LongRange, CharRange
            val numRange : IntRange = 1..5

            println(numRange.contains(3))
            println(numRange.contains(6))
            println(numRange.first)

            val charRange: CharRange = 'a'..'d'

            println(charRange.contains('c'))
            println(charRange.contains('e'))
            println(charRange.last)
        }

        private fun forLoop() {
            // for 문
            for(i in 1..10) {
                print(i)
            }

            for(i in 10 downTo 3) {
                print(i)
            }

            for(i in 3..20 step 3) {
                print(i)
            }

            val fruits = arrayOf("apple", "pear", "watermelon", "banana")

            for(fruit in fruits) {
                print("$fruit ")
            }

            for((idx, fruit) in fruits.withIndex()) {
                println("Index = $idx, fruit = $fruit")
            }
        }

        private fun whileLoop() {
            var num = 1
            while(num < 5) {
                println(num)
                num++
            }

            num = 3
            do {
                num++
                println(num)
            } while(num < 5)
        }

        private fun ifs() {
            // statement = 명령문 => 어떤 동작을 수행
            val examScore = 100
            if(examScore == 100) {
                println("만점이네용~")
            } else {
                println("만점은 아니에용ㅠㅠ")
            }

            // expression = 표현식 => 하나의 값으로 평가되는 문장
            val myAge = 40
            val isAdult = if(myAge > 30) true else false
//            val isAdult = myAge > 30
            println("성인여부 = $isAdult")
        }

        private fun whens() {
            val weather = 15
            when(weather) {
                -20 -> {
                    println("매우 추운 날씨")
                }
                11,12,13,14 -> {println("쌀쌀한 날씨")}
                in 15..26 -> {println("활동하기 좋은 날씨")}
                // 범위 안에 안들어가는 경우
                !in -30..50 -> {println("잘못된 값입니다. -30 ~ 50 사이의 값을 적어주세요")}
                else -> {println("잘 모르겠는 값")} // 위의 경우 모두 아닐때
            }

            // 표현식 => 값을 반환하는 것
            val essayScore = 95
            val grade = when(essayScore) {
                in 0..40 -> "D"
                in 41..70 -> "C"
                in 71..90 -> "B"
                else -> "A"
            }

            println("에세이 학점 : $grade")
        }
    }


}