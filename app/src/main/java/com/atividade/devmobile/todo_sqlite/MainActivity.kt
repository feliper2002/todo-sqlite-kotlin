package com.atividade.devmobile.todo_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.todo_sqlite.data.SqliteHelper
import com.atividade.devmobile.todo_sqlite.functions.AppFunctions
import com.atividade.devmobile.todo_sqlite.todo.ToDoAdapter
import com.atividade.devmobile.todo_sqlite.todo.TodoModel
import kotlinx.android.synthetic.main.todo_card.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnAdd: Button
    private lateinit var titleText: EditText
    private lateinit var sqlHelper: SqliteHelper

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()

        sqlHelper = SqliteHelper(this)
        getTodos()

        btnAdd.setOnClickListener{ addTodo() }

        adapter.setOnClickDeleteTodo { deleteTodo(it.id) }
    }

    private fun initView() {
        btnAdd = findViewById(R.id.addToDo)
        titleText = findViewById(R.id.todoInput)
        recyclerView = findViewById(R.id.reclyclerView)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ToDoAdapter()
        recyclerView.adapter = adapter
    }

    private fun addTodo() {
        val title = titleText.text.toString()

        val todo = TodoModel(id = AppFunctions.randomID(title), title = title)
        val status = sqlHelper.insertTodo(todo)
        if (status > -1) {
            getTodos()
            clearInputText()
        }
    }

    private fun getTodos() {
        val list = sqlHelper.getTodos()
        adapter.setDataset(list)
        println(list)
    }

    private fun deleteTodo(id: String) {
        sqlHelper.deleteTodo(id)
        getTodos()
    }

    private fun clearInputText() {
        titleText.text.clear()
    }
}