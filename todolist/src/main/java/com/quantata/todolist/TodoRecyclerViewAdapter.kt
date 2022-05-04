package com.quantata.todolist

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quantata.todolist.databinding.ItemTodoBinding
import com.quantata.todolist.db.ToDoEntity

class TodoRecyclerViewAdapter(private val todoList: ArrayList<ToDoEntity>)
    : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        // View Binding 에서 기본적으로 제공하는 root 변수는 레이아웃의 root 레이아웃을 의미합니다.
        val root = binding.root
    }

    // MyViewHolder 클래스에서 만든 뷰 홀더 객체를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // item_todo.xml view Binding 객체 생성
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // val todoList(매개변수로 받은 데이터)를 onCreateViewHolder() 객체에 어떻게 넣어줄 것인지 결정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        // 중요도에 따라 색상을 변경
        when(todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        // 중요도에 따라 중요도 텍스트(1, 2, 3) 변경
        holder.tv_importance.text = todoData.importance.toString()
        // 할 일의 제목 변경
        holder.tv_title.text = todoData.title
    }

    // 데이터가 몇개인지 변환
    override fun getItemCount(): Int {
        return todoList.size
    }


}