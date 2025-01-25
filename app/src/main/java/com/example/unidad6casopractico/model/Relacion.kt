package com.example.unidad6casopractico.model

import java.io.Serializable

class Relacion {

    data class CinePelicula(
        val idCine: Int,
        val idPelicula: Int
    ): Serializable
}
