package com.example.unidad6casopractico.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unidad6casopractico.databinding.ActivityEditarNombrePeliculaBinding

class EditarNombrePeliculaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarNombrePeliculaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarNombrePeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombrePeli = intent.getStringExtra("nombrePeli") ?: ""
        val portadaPeli = intent.getIntExtra("portadaPeli",0) ?: ""

        binding.editTextNombrePelicula.setText(nombrePeli)
        binding.imageViewPortada.setImageResource(portadaPeli as Int)
        binding.btnGuardar.setOnClickListener {
            val nuevoNombre = binding.editTextNombrePelicula.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("nombrePeli", nuevoNombre)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }
}
