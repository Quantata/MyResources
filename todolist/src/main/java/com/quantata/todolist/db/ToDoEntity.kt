package com.quantata.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // 어떤 구성요소인지를 알려주려면 꼭 어노테이션을 써주어야 합니다.
data class ToDoEntity ( // data 클래스의 주된 목적 : data 를 가지고 있는 것. // 엔티티클래스는 할 일 정보면 가지고 있기만 하면 되므로 데이터 클래스로 만들기 적합
    @PrimaryKey(autoGenerate = true) val id : Int? = null, //
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "importance") val importance: Int
)