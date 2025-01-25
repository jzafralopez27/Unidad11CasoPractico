package com.example.unidad6casopractico.model

import java.io.Serializable

data class Pelicula(
    val id: Int,
    var titulo: String,
    val descripcion: String,
    val imagen: Int,
    val duracion: Int,
    val anho: Int,
    val pais: String,
    var uri: String,
    var cines: List<Cine> = listOf()
) : Serializable