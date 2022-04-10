package com.atividade.devmobile.todo_sqlite.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.atividade.devmobile.todo_sqlite.todo.TodoModel

class SqliteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val query = "CREATE TABLE $TABLE_NAME( $ID VARCHAR, $TITLE VARCHAR);"

        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        val query = "DROP TABLE IF EXISTS $TABLE_NAME;"

        db.execSQL(query)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ToDo.db"
        private const val TABLE_NAME = "todo"
        private const val TITLE = "title"
        private const val ID = "id"
    }

    fun insertTodo(todo: TodoModel): Long {
        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(ID, todo.id)
        contentValues.put(TITLE, todo.title)

        val status = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return status
    }

    fun deleteTodo(id: String) {
        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(ID, id)

        db.delete(TABLE_NAME, "id=?", arrayOf(id))
        db.close()
    }

    fun getTodos(): ArrayList<TodoModel> {
        val list = ArrayList<TodoModel>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: String
        var title: String

        if (cursor.moveToFirst()) {
            do {
                val id_index: Int = cursor.getColumnIndex("$ID")
                val title_index: Int = cursor.getColumnIndex("$TITLE")

                id = cursor.getString(id_index)
                title = cursor.getString(title_index)

                val todo = TodoModel(id = id, title = title)

                list.add(todo)
            } while (cursor.moveToNext())
        }
        return list
    }

}