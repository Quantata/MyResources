package com.quantata.todolist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao // 어떤 구성요소인지 알려주려면 꼭 어노테이션을 써야 합니다.
interface ToDoDao {
    @Query("SELECT * FROM ToDoEntity")
    fun getAll() : List<ToDoEntity> // ToDoEntity 에서 모든 데이터를 불러오는 쿼리 함수

    @Insert
    fun insertTodo(todo: ToDoEntity) // ToDoEntity 객체를 테이블에 삽입하는 함수

    @Delete
    fun deleteTodo(todo: ToDoEntity) // TodoEntity 객체를 테이블에서 삭제하는 함수
}