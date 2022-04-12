package com.atividade.devmobile.todo_sqlite.functions

import android.widget.Toast
import kotlin.math.roundToInt

class AppFunctions {

    companion object {
        fun randomID(title: String): String {
            /*
            Este método utiliza uma String como uma forma de `modificador` para
            através de uma lógica simples e genérica criar um ID personalizado
            apenas para identificação dos ToDo no banco de dados
            */
            var lower = title.lowercase()

            lower += "ID"

            for (i in 0..4) {
                val random = Math.random().roundToInt()
                lower += "$random"
            }
            lower += "todoid"

            return lower
        }
    }
}