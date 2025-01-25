package com.example.unidad6casopractico.contract

import android.provider.BaseColumns

class PeliContract {
    companion object {
        val NOMBRE_BD = "peliculas.db"
        val VERSION_BD = 1
        class Entrada: BaseColumns{
            companion object {
                val TABLA = "pelicula"
                val IDCOL = "id"
                val NOMBRECOL = "nombre"
                val DESCRIPCIONCOL = "descripcion"
                val IMAGENCOL = "imagen"
                val DURACIONCOL = "duracion"
                val ANIOCOL = "anio"
                val PAISCOL = "pais"
                val URICOL = "uri"
            }
        }
    }
}