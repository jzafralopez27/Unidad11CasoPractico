package com.example.unidad6casopractico.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.unidad6casopractico.model.Pelicula
import com.example.unidad6casopractico.R
import com.example.unidad6casopractico.contract.PeliContract

class DBOpenHelper private constructor(context: Context?) :
    SQLiteOpenHelper(context, PeliContract.NOMBRE_BD, null, PeliContract.VERSION_BD) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(
                "CREATE TABLE ${PeliContract.Companion.Entrada.TABLA} (" +
                        "${PeliContract.Companion.Entrada.IDCOL} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "${PeliContract.Companion.Entrada.NOMBRECOL} NVARCHAR(200), " +
                        "${PeliContract.Companion.Entrada.DESCRIPCIONCOL} NVARCHAR(200), " +
                        "${PeliContract.Companion.Entrada.IMAGENCOL} INT, " +
                        "${PeliContract.Companion.Entrada.DURACIONCOL} INT, " +
                        "${PeliContract.Companion.Entrada.ANIOCOL} INT, " +
                        "${PeliContract.Companion.Entrada.PAISCOL} NVARCHAR(200)," +
                        "${PeliContract.Companion.Entrada.URICOL} NVARCHAR(200)" +
                        ")"
            )
            inicializarBBDD(sqLiteDatabase)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun inicializarBBDD(db: SQLiteDatabase) {
        val lista = cargarPeliculas()
        for (pelicula in lista) {
            db.execSQL(
                ("INSERT INTO ${PeliContract.Companion.Entrada.TABLA}(" +
                        "${PeliContract.Companion.Entrada.NOMBRECOL}," +
                        "${PeliContract.Companion.Entrada.DESCRIPCIONCOL}," +
                        "${PeliContract.Companion.Entrada.IMAGENCOL}," +
                        "${PeliContract.Companion.Entrada.DURACIONCOL}," +
                        "${PeliContract.Companion.Entrada.ANIOCOL}," +
                        "${PeliContract.Companion.Entrada.PAISCOL}," +
                        "${PeliContract.Companion.Entrada.URICOL})" +
                        " VALUES ('${pelicula.titulo}', '${pelicula.descripcion}', ${pelicula.imagen}, ${pelicula.duracion}, ${pelicula.anho}, '${pelicula.pais}', '${pelicula.uri});")
            )
        }
    }

    private fun cargarPeliculas(): MutableList<Pelicula> {
        return mutableListOf(
            Pelicula(
                1,
                "La vida es bella",
                "Un padre judío-italiano que utiliza su imaginación y humor para proteger a su hijo de los horrores de un campo de concentración nazi, transformando la tragedia en un juego para mantener viva la esperanza.",
                R.drawable.la_vida_es_bella,
                116,
                1997,
                "Italia",
                ""
            ),
            Pelicula(
                2,
                "El padrino",
                "El envejecido patriarca de una dinastía del crimen organizado transfiere el control de su imperio clandestino a su hijo reacio.",
                R.drawable.elpadrino,
                175,
                1972,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                3,
                "El caballero oscuro",
                "Cuando la amenaza conocida como El Joker emerge de su pasado misterioso, causa estragos y caos en la gente de Gotham.",
                R.drawable.el_caballero_oscuro,
                152,
                2008,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                4,
                "Pulp Fiction",
                "La vida de dos sicarios de la mafia, un boxeador, la esposa de un gánster y dos bandidos se entrelazan en cuatro historias de violencia y redención.",
                R.drawable.pulpfiction,
                153,
                1994,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                5,
                "El Señor de los Anillos",
                "Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron para distraerlo de Frodo y Sam mientras se acercan al Monte del Destino con el Anillo Único.",
                R.drawable.senhoranillos,
                201,
                2003,
                "Nueva Zelanda",
                ""
            ),
            Pelicula(
                6,
                "Forrest Gump",
                "Los presidios de Forrest Gump, un hombre con un coeficiente intelectual bajo, tienen lugar durante varios eventos históricos estadounidenses.",
                R.drawable.forrestgump,
                142,
                1994,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                7,
                "Origen",
                "Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartición de sueños recibe la tarea inversa de plantar una idea en la mente de un director ejecutivo.",
                R.drawable.origen,
                148,
                2010,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                8,
                "El club de la lucha",
                "Un oficinista insomne y un fabricante de jabón desmotivado forman un club de lucha clandestino que se convierte en algo mucho más grande.",
                R.drawable.clublucha,
                139,
                1999,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                9,
                "El Imperio Contraataca",
                "Después de que los rebeldes sean brutalmente sobrepasados por el Imperio en el planeta helado Hoth, Luke Skywalker comienza su entrenamiento Jedi con Yoda, mientras sus amigos son perseguidos por Darth Vader.",
                R.drawable.imperiocontraataca,
                124,
                1980,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                10,
                "El bueno, el malo y el feo",
                "Un cazarrecompensas se asocia con un hombre para encontrar una fortuna en oro enterrado en un cementerio remoto.",
                R.drawable.buenomalofeo,
                161,
                1966,
                "Italia",
                ""
            ),
            Pelicula(
                11,
                "Matrix",
                "Un hacker informático aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.",
                R.drawable.matrix,
                131,
                1999,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                12,
                "Uno de los nuestros",
                "La historia de Henry Hill y su vida en la mafia, abarcando su relación con su esposa Karen Hill y sus socios mafiosos Jimmy Conway y Tommy DeVito.",
                R.drawable.uno_de_los_nuestros,
                148,
                1990,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                13,
                "La lista de Schindler",
                "En la Polonia ocupada por los alemanes durante la Segunda Guerra Mundial, Oskar Schindler se preocupa gradualmente por su fuerza laboral judía después de presenciar su persecución por parte de los nazis.",
                R.drawable.la_lista_de_schindler,
                195,
                1993,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                14,
                "Interestelar",
                "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
                R.drawable.interstellar,
                169,
                2014,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                15,
                "Parásitos",
                "La codicia y la discriminación de clase amenazan la recién formada relación simbiótica entre la adinerada familia Park y el empobrecido clan Kim.",
                R.drawable.parasitos,
                132,
                2019,
                "Corea del sur",
                ""
            ),
            Pelicula(
                16,
                "La milla verde",
                "Las vidas de los guardias en el corredor de la muerte se ven afectadas por uno de sus reclusos: un hombre negro acusado de asesinato infantil y violación, pero que tiene un don misterioso.",
                R.drawable.la_milla_verde,
                180,
                1999,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                17,
                "Cadena perpetua",
                "Dos hombres encarcelados establecen una fuerte amistad a lo largo de los años, encontrando consuelo y redención eventual a través de actos de decencia común.",
                R.drawable.cadena_perpetua,
                142,
                1994,
                "Estados Unidos",
                ""
            ),
            Pelicula(
                18,
                "El pianista",
                "Narra la historia real de Władysław Szpilman, un pianista judío-polaco que lucha por sobrevivir durante la ocupación nazi en la Segunda Guerra Mundial, utilizando su talento y resiliencia en medio del horror.",
                R.drawable.el_pianista,
                148,
                2002,
                "Reino Unido",
                ""
            ),
            Pelicula(
                19,
                "Estori Boris",
                "La peeeeeehlaa... ziii... la moráaa... kompramelaaaaa",
                R.drawable.faliyo,
                102,
                2024,
                "Er palo",
                ""
            ),
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${PeliContract.Companion.Entrada.TABLA}")
        onCreate(db!!)
    }


    companion object {
        private var dbOpen: DBOpenHelper? = null
        fun getInstance(context: Context?): DBOpenHelper? {
            if (dbOpen == null) {
                dbOpen = DBOpenHelper(context)
            }
            return dbOpen
        }
    }


}