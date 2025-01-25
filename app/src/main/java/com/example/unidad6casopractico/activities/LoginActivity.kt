package com.example.unidad6casopractico.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unidad6casopractico.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private var preferencias: SharedPreferences? = null
    private var usuario: String? = ""
    private var password: String? = ""
    private var recordar: Boolean = false
    private lateinit var binding : ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencias = getSharedPreferences("preferencias", MODE_PRIVATE)
        if (preferencias!!.contains("recordar")) {
            recordar = preferencias!!.getBoolean("recordar", false)
            binding.cbRecordar.isChecked = recordar
        }

        if (recordar){
            if (preferencias!!.contains("usuario") && preferencias!!.contains("password")) {
                usuario = preferencias!!.getString("usuario", "")
                password = preferencias!!.getString("password", "")
                binding.etUsuario.setText(usuario)
                binding.etPassword.setText(password)
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etUsuario.text.toString().length> 0){
                usuario = binding.etUsuario.text.toString()
                password = binding.etPassword.text.toString()
                val editor = preferencias!!.edit()
                editor.putBoolean("recordar", binding.cbRecordar.isChecked)
                if (binding.cbRecordar.isChecked){
                    editor.putString("usuario", usuario)
                    editor.putString("password", password)
                }
                editor.apply()

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("usuario", usuario)
                intent.putExtra("password", password)
                startActivity(intent)
            }
        }

    }

}




