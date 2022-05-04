package com.quantata.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.quantata.todolist.databinding.ActivityAddTodoBinding
import com.quantata.todolist.db.AppDatabase
import com.quantata.todolist.db.ToDoDao
import com.quantata.todolist.db.ToDoEntity

class AddTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var db: AppDatabase // AppDatabase 는 추상 클래스
    private lateinit var todoDao: ToDoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()  // 추상 함수 사용 // 추상함수는 db(AppDatabase 객체) 에서 반드시 오버라이딩 해야만 사용할 수 있는 함수

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    /**
     * @desc 할 일 추가 함수
     */
    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString() // 할 일의 제목
        var todoImportance = binding.radioGroup.checkedRadioButtonId // 할 일의 중요도

        // 어떤 버튼이 눌렸는지 확인하고 값을 지정해줍니다.
        todoImportance = when(todoImportance) {
            R.id.btn_high -> {
                1
            }
            R.id.btn_middle -> {
                2
            }
            R.id.btn_low -> {
                3
            }
            else -> {
                -1
            }
        }

        // 중요도가 선택되지 않거나, 제목이 작성되지 않는지 체크합니다.
        if(todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        } else  {
            Thread { // background Thread 실행, 실무에서는 Coroutines, RxJava, RxKotlin 과 같은 라이브러리를 사용하여 스레드를 컨트롤하고 비동기 처리 함.
                todoDao.insertTodo(ToDoEntity(null, todoTitle, todoImportance)) // 추상함수를 통해
                runOnUiThread { // 아래 작업은 UI Thread 에서 실행해 주어야 합니다.
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                    finish() // AddTodoActivity 종료, 다시 MainActivity 로 돌아감
                }
            }.start()
        }

    }
}