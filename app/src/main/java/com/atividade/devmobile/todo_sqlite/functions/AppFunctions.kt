package com.atividade.devmobile.todo_sqlite.functions

import android.widget.Toast
import kotlin.math.roundToInt

class AppFunctions {

    companion object {
        fun randomID(title: String): String {
            var lower = title.lowercase()

            lower += "ID"

            for (i in 0..4) {
                val random = Math.random().roundToInt()
                lower += "$random"
            }
            lower += "todoid"

            return lower
        }

        fun convertChecked(value: Int): Boolean {
            if (value == 0) return false
            return true
        }
    }

}