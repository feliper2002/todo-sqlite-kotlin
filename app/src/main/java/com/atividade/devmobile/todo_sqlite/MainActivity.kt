package com.atividade.devmobile.todo_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atividade.devmobile.todo_sqlite.data.SqliteHelper
import com.atividade.devmobile.todo_sqlite.functions.AppFunctions
import com.atividade.devmobile.todo_sqlite.todo.ToDoAdapter
import com.atividade.devmobile.todo_sqlite.todo.TodoModel

class MainActivity : AppCompatActivity() {

    /*
    Utilizei a palavra reservada `lateinit` para informar ao kotlin
    que as referentes variáveis seriam inicilizadas ou instanciadas tardiamente
    */

    private lateinit var btnAdd: Button
    private lateinit var titleText: EditText
    private lateinit var sqlHelper: SqliteHelper

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ToDoAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSearchView()

        initView()
        initRecyclerView()

        sqlHelper = SqliteHelper(this)
        getTodos()

        btnAdd.setOnClickListener{ addTodo() }

        adapter.setOnClickDeleteTodo { deleteTodo(it.id) }
    }

    private fun initView() {
        /*
        Este método foi utilizado para inicializar as variáveis referentes à
        parte visual do aplicativo:
        - Botão de adicionar ToDo
        - Input de título
        - RecyclerView
        */
        btnAdd = findViewById(R.id.addToDo)
        titleText = findViewById(R.id.todoInput)
        recyclerView = findViewById(R.id.reclyclerView)
    }

    private fun initSearchView() {
        searchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                val list = sqlHelper.getTodos()
                adapter.setDataset(list)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter.items.filter { todo -> todo.title.contains("$newText") }.isNotEmpty()) {
                    var array = adapter.items.filter { todo -> todo.title.contains("$newText") }
                    var arrayList = ArrayList<TodoModel>()

                    for (e in array) {
                        arrayList.add(e)
                    }

                    println(arrayList)
                    adapter.setDataset(arrayList)
                } else if (newText.equals("")) {
                    val list = sqlHelper.getTodos()
                    adapter.setDataset(list)
                } else {
                    val list = sqlHelper.getTodos()
                    adapter.setDataset(list)
                }
                return false
            }
        })
    }

    private fun initRecyclerView() {
        /*
        Este método foi utilizado para inicializar e instanciar variáveis e valores
        referentes ao RecyclerView. Bem como seu LayoutManager e adapter.
        */
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ToDoAdapter()
        recyclerView.adapter = adapter
    }

    private fun addTodo() {
        /*
        Este método se comunica com o método `inserTodo()` da classe [SQLiteHelper]

        Verifica se o texto inserido no input está vazio
        Caso `true`:
            - Exibe [Toast] com mensagem de que o texto está vazio
            - Não realiza nenhuma ação do [SQLiteHelper]
        Caso `false`:
            - Exibe [Toast] de ToDo criado com sucesso
            - Adiciona o título ao [TodoModel] e gera um ID aleatório
            - Se comunica com o [SQLiteHelper] para inserir o ToDo no banco de dados
            - Atualiza a lista de ToDo com o método `getTodos()`
            - Reseta o texto do input
        */
        val title = titleText.text.toString()

        if (title.isNotEmpty()) {
            val todo = TodoModel(id = AppFunctions.randomID(title), title = title)
            val status = sqlHelper.insertTodo(todo)
            if (status > -1) {
                Toast.makeText(this, "$title criado!", Toast.LENGTH_SHORT).show()
                getTodos()
                clearInputText()
            }
        } else {
            Toast.makeText(this, "Título do ToDo não pode ser vazio!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTodos() {
        /*
        Este método resgata da classe [SQLiteHelper] os dados que estão inseridos
        na tabela `ToDo` do banco de dados.

        Após atualizar estes dados, o [adapter] é notificado para exibir esses valores
        no [RecyclerView]
        */
        val list = sqlHelper.getTodos()
        adapter.setDataset(list)
        println(list)
    }

    private fun deleteTodo(id: String) {
        /*
        Este método deleta o ToDo da referente tabela no banco de dados através de seu ID.

        Após isso, atualiza novamente a lista de ToDos com o método `getTodos()`
        */
        sqlHelper.deleteTodo(id)
        Toast.makeText(this, "ToDo deletado!", Toast.LENGTH_SHORT).show()
        getTodos()
    }

    private fun clearInputText() {
        titleText.text.clear()
    }
}