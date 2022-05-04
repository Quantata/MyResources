package com.quantata.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database : 데이터베이스 어노테이션으로 데이터베이스 클래스임을 명시하고 entities 에 사용될 엔티티 클래스를 나열하면 됩니다.
// 이번에는 엔티티가 하나이므로 하나만 적어줬지만, 여러개일 경우 콤마로 구분하여 나열.
@Database(entities = arrayOf(ToDoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() { // RoomDatabase 클래스를 상속받는 추상 클래스

    abstract fun getTodoDao() : ToDoDao // ToDoDao 를 반환하는 추상 함수

    companion object {
        val databaseName = "db_todo" // 데이터 베이스 이름
        var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            // appDatabase 가 null 이면 객체를 생성하고 null 이 아니면 기존 객체를 반환하는 싱글턴 패턴 getInstance() 함수 구현
            if(appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    databaseName).build()
            }

            return appDatabase
        }
    }
}