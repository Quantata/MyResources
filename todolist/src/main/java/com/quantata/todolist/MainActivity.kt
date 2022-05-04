package com.quantata.todolist

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Dao
import com.quantata.todolist.databinding.ActivityMainBinding
import com.quantata.todolist.db.AppDatabase
import com.quantata.todolist.db.ToDoDao
import com.quantata.todolist.db.ToDoEntity

class MainActivity : AppCompatActivity(), OnItemLongClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db : AppDatabase
    private lateinit var todoDao: ToDoDao
    private lateinit var todoList : ArrayList<ToDoEntity>
    private lateinit var adapter : TodoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        // DB 인스턴스를 가져오고 DB 작업을 할 수 있는 DAO 를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()
    }

    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // 리사이클 뷰 설정
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList, this) // 어댑터 객체 할당
            binding.recyclerView.adapter = adapter // 리사이클러 뷰 어댑터로 위에서 만든 어댑터 설정
            binding.recyclerView.layoutManager = LinearLayoutManager(this) // 레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("할 일 삭제") // 제목 설정
        builder.setMessage("정말 삭제하시겠습니까?") // 내용 설정
        builder.setNegativeButton("취소", null) // 취소 버튼 설정
        builder.setPositiveButton("네",
            object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)
                }
            })

        builder.show()
    }

    private fun deleteTodo(position: Int) {
        Thread {
            todoDao.deleteTodo(todoList[position]) // DB 에서 삭제
            todoList.removeAt(position) // 리스트에서 삭제
            runOnUiThread {
//                adapter.notifyDataSetChanged()
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}