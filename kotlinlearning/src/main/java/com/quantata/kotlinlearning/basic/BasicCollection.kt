package com.quantata.kotlinlearning.basic

import android.os.Build

class BasicCollection {
    companion object { // object 키워드로 만들어진 객체는 여러 번 호출되더라도 딱 하나의 객체만 생성되어 재사용
        fun result() {
            listFunc()
            setFunc()
            mapFunc()
        }

        // List : 순서가 있는 자료구조
        private fun listFunc() {
            // 읽기 전용 리스트 : listOf()
            val numImmutableList = listOf(1,2,3)
//            numImmutableList[1] = 1 // 오류발생, 읽기전용이기 때문

             // 읽기 쓰기 가능한 리스트 : mutableListOf()
            val numMutableList = mutableListOf(1,2,3)
            numMutableList[0] = 23

            println(numMutableList)
            println(numImmutableList[0])

        }

        // Set : 순서가 없는 자료구조, 중복되지 않은 요소들로 만들어짐, 같은 값을 추가해도 해당 값은 하나만 저장
        private fun setFunc() {
            // 읽기 전용 Set : setOf()
            val immutableSet = setOf(1,1,2,2,3,3)
            println(immutableSet) // [1, 2, 3]

            // 읽기 쓰기 모두 가능한 Set : mutableSetOf()
            val mutableSet = mutableSetOf(1,1,2,2,3,3,3)
            mutableSet.add(100) // true
            mutableSet.remove(1) // true
            mutableSet.remove(4) // false

            println(mutableSet) // [2, 3, 100]
            println(mutableSet.contains(1)) // false

        }

        // Map : 키와 값을 짝지어 저장하는 자료구조, 키는 중복될 수 없음
        private fun mapFunc() {
            // 읽기 전용 Map : mapOf()
            val immutableMap = mapOf("hello" to "nice to meet u", "I am Fine" to "thx u", "Day" to 15, "Day" to 13)
            print(immutableMap) // Day 가 중복이 됐는데, 중복 값은 안됨으로 뒨에 정의한 값 13으로 나옴

            // 읽기 쓰기 모두 가능한 Map : mutableMapOf()
            val mutableMap = mutableMapOf("모밀" to "일식", "짜장면" to "한식", "칼국수" to "한식")
            mutableMap["막국수"] = "한식"
            mutableMap.remove("모밀")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mutableMap.replace("짜장면", "한식", "중식")
            } else {
                mutableMap["짜장면"] = "중식"
            }
            println(mutableMap)
        }

    }
}