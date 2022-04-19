package com.quantata.kotlinlearning

class BasicRangeClass {
    companion object {
        fun result() {
            rangeClass()
            forLoop()

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
    }
}