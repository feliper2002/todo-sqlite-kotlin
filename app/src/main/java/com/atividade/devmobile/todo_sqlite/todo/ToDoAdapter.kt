package com.atividade.devmobile.todo_sqlite.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_card.view.*
import com.atividade.devmobile.todo_sqlite.R

class ToDoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
        Esta classe é responsável por fazer toda a `adaptação` dos dados inseridos
        no banco de dados do SQLite, permitindo a conexão com a interface do aplicativo

        O `Adapter` utilizado para este caso foi o [RecyclerView.Adapter], Widget
        comumente utilizado para exibição de objetos filhos em formato de lista
     */

    var items: ArrayList<TodoModel> = ArrayList()
    private var onClickDeleteItem: ((TodoModel)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is ProductViewHolder -> {
                holder.bind(items[position])
                holder.deleteBtn.setOnClickListener { onClickDeleteItem?.invoke(items[position]) }
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnClickDeleteTodo(callback: (TodoModel)->Unit) {
        this.onClickDeleteItem = callback
    }


    fun setDataset(todos: ArrayList<TodoModel>) {
        this.items = todos
        notifyDataSetChanged()
    }

    class ProductViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val todoTitle = itemView.card_title
        var deleteBtn = itemView.todo_delete_btn

        fun bind(todo: TodoModel) {
            todoTitle.text = todo.title

        }
    }

}