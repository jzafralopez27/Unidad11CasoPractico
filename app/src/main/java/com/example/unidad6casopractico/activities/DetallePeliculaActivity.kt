package com.example.unidad6casopractico.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unidad6casopractico.databinding.DetallePeliculaBinding
import com.example.unidad6casopractico.model.Pelicula


class DetallePeliculaActivity : AppCompatActivity() {
    private lateinit var binding: DetallePeliculaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetallePeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pelicula = intent.getSerializableExtra("pelicula") as Pelicula

        binding.textViewTitulo.text = pelicula.titulo
        binding.textViewDescripcion.text = pelicula.descripcion
        binding.imageViewPoster.setImageResource(pelicula.imagen)
    }
}
