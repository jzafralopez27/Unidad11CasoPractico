package com.example.unidad6casopractico.model

import java.io.Serializable

data class Cine(
    val id: Int,
    val nombre: String,
    val ciudad: String,
    val latitud: Double,
    val longitud: Double
): Serializable

val listaCines = listOf(
    Cine(1, "Cine Madrid", "Madrid", 40.4168, -3.7038),
    Cine(2, "Cine Barcelona", "Barcelona", 41.3784, 2.1925),
    Cine(3, "Cine Valencia", "Valencia", 39.4699, -0.3763),
    Cine(4, "Cine Sevilla", "Sevilla", 37.3886, -5.9823),
    Cine(5, "Cine Málaga", "Málaga", 36.7213, -4.4216),
    Cine(6, "Cine Bilbao", "Bilbao", 43.2630, -2.9350),
    Cine(7, "Cine Zaragoza", "Zaragoza", 41.6488, -0.8891),
    Cine(8, "Cine Granada", "Granada", 37.1773, -3.5986),
    Cine(9, "Cine Alicante", "Alicante", 38.3460, -0.4907),
    Cine(10, "Cine Salamanca", "Salamanca", 40.9700, -5.6630))
