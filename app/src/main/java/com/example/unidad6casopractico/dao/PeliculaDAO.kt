package com.example.unidad6casopractico.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.unidad6casopractico.contract.PeliContract
import com.example.unidad6casopractico.model.Pelicula


class PeliculaDAO {
    fun cargarLista(context: Context?): MutableList<Pelicula> {
        val res = mutableListOf<Pelicula>()
        var c: Cursor? = null  // Cambiar a una variable nullable para evitar el uso de lateinit
        try {
            // Obtener acceso de solo lectura
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val columnas = arrayOf(
                PeliContract.Companion.Entrada.IDCOL,
                PeliContract.Companion.Entrada.NOMBRECOL,
                PeliContract.Companion.Entrada.DESCRIPCIONCOL,
                PeliContract.Companion.Entrada.IMAGENCOL,
                PeliContract.Companion.Entrada.DURACIONCOL,
                PeliContract.Companion.Entrada.ANIOCOL,
                PeliContract.Companion.Entrada.PAISCOL,
                PeliContract.Companion.Entrada.URICOL
            )

            c = db.query(
                PeliContract.Companion.Entrada.TABLA,
                columnas, null, null, null, null, null
            )

            // Leer resultados del cursor e insertarlos en la lista
            while (c?.moveToNext() == true) {
                val nueva = Pelicula(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7)
                )
                res.add(nueva)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            c?.close()  // Asegurarse de que el cursor se cierre incluso si ocurre una excepción
        }
        return res
    }


    fun actualizarBBDD(context: Context?, peli: Pelicula) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        /*db.execSQL(
            "UPDATE Pelis "
                    + "SET nombre='${Peli.nombre}' " +
                    "SET descripcion='${Peli.descripcion}'" +
                    "SET imagen='${Peli.imagen}'" +
                    "WHERE id=${Peli.id};"
        )
        */
        val values = ContentValues()
        values.put(PeliContract.Companion.Entrada.IDCOL,peli.id)
        values.put(PeliContract.Companion.Entrada.NOMBRECOL,peli.titulo)
        values.put(PeliContract.Companion.Entrada.DESCRIPCIONCOL,peli.descripcion)
        values.put(PeliContract.Companion.Entrada.IMAGENCOL,peli.imagen)
        values.put(PeliContract.Companion.Entrada.DURACIONCOL,peli.uri)
        db.update(PeliContract.Companion.Entrada.TABLA,values,"id=?",arrayOf(peli.id.toString()))
        db.close()
    }

    fun insertarBBDD(context: Context?, peli:Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "INSERT INTO Pelis (titulo, descripcion, imagen, uri) VALUES "
                    + "('${peli.titulo}', " +
                    "'${peli.descripcion}', ${peli.imagen}, ${peli.uri});"
        )
        db.close()
    }

    fun eliminar(context: Context?, peli:Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "DELETE FROM Pelis WHERE id= ${peli.id};"
        )

        /* val values = arrayOf((Peli.id).toString())
         db.delete(PeliContract.Companion.Entrada.NOMBRE_TABLA,"id=?",values)*/
        db.close()
    }

    fun getPelicula(context: Context?, id: Int): Pelicula? {
        var pelicula: Pelicula? = null
        try {
            // Verificar que el context no sea null
            val db = context?.let { DBOpenHelper.getInstance(it)!!.readableDatabase }
            if (db == null) {
                throw Exception("No se pudo acceder a la base de datos")
            }

            // Consulta SQL usando parámetros en lugar de concatenación
            val sql = "SELECT * FROM ${PeliContract.Companion.Entrada.TABLA} WHERE ${PeliContract.Companion.Entrada.IDCOL} = ?"
            val result = db.rawQuery(sql, arrayOf(id.toString()))  // Usamos arrayOf para pasar el id como parámetro

            // Si hay resultados, construye el objeto Pelicula
            if (result.moveToFirst()) {
                pelicula = Pelicula(
                    result.getInt(0), // ID
                    result.getString(1), // Título
                    result.getString(2), // Descripción
                    result.getInt(3), // Imagen
                    result.getInt(4), // Duración
                    result.getInt(5), // Año
                    result.getString(6), // País
                    result.getString(7) // URI
                )
            }

            // Cerrar el resultado
            result.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pelicula
    }





}