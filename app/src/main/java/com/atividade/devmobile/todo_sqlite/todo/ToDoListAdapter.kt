package com.atividade.devmobile.todo_sqlite.todo

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.atividade.devmobile.todo_sqlite.R
import kotlinx.android.synthetic.main.todo_card.view.*

class ToDoListAdapter(context: Activity, private var itens: ArrayList<TodoModel>): ArrayAdapter<TodoModel>(context, R.layout.todo_card, itens) {

    private var onClickDeleteItem: ((TodoModel)->Unit)? = null

    fun setOnClickDeleteTodo(callback: (TodoModel)->Unit) {
        this.onClickDeleteItem = callback
    }


    fun setDataset(todos: ArrayList<TodoModel>) {
        itens = todos
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)

        val todoTitle = view.card_title
        var deleteBtn = view.todo_delete_btn

        todoTitle.text = "${itens[position]}"
        deleteBtn.setOnClickListener { onClickDeleteItem?.invoke(itens[position]) }

        return view
    }



}