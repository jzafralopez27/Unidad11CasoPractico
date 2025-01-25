package com.example.unidad6casopractico.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unidad6casopractico.R
import com.example.unidad6casopractico.adapter.PeliculaAdapter
import com.example.unidad6casopractico.dao.PeliculaDAO
import com.example.unidad6casopractico.databinding.ActivityMainBinding
import com.example.unidad6casopractico.model.Cine
import com.example.unidad6casopractico.model.Pelicula
import com.example.unidad6casopractico.model.listaCines
import com.example.unidad6casopractico.provider.PeliculaProvider
import com.google.android.material.snackbar.Snackbar
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listaPelis: MutableList<Pelicula>
    private lateinit var adapter: PeliculaAdapter
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var posicion: Int = -1 // con esta variable guardo la posición de la película
                                   // que se quiere editar
    private lateinit var miDAO: PeliculaDAO
    private var listaVacia: Boolean = false;
    private lateinit var map: MapView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        miDAO = PeliculaDAO()
        listaPelis = miDAO.cargarLista(this)
        asignarCinesAleatorios()

        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvPelis.layoutManager = linearLayoutManager

        adapter = PeliculaAdapter(listaPelis) { pelicula ->
            val toastMessage = "Duración: ${pelicula.duracion} minutos – Año: ${pelicula.anho}"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        binding.rvPelis.adapter = adapter

        binding.rvPelis.setHasFixedSize(true)
        binding.rvPelis.itemAnimator = DefaultItemAnimator()
        setupSwipeRefresh()

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val nuevoNombre = result.data?.getStringExtra("nombrePeli").toString()
                if (posicion != -1) {
                    listaPelis[posicion].titulo = nuevoNombre
                    adapter.notifyItemChanged(posicion)
                    posicion = -1
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(peli: String?): Boolean {
                filterList(peli)
                return true
            }
        })
    }

    private fun filterList(peli0: String?) {
        if (peli0 != null) {
            var filteredLista = mutableListOf<Pelicula>()
            if (peli0.isNotEmpty() && !listaVacia) {
                listaPelis = miDAO.cargarLista(this)
                for (i in listaPelis) {
                    if (i.titulo.lowercase(Locale.ROOT).contains(peli0.lowercase())) {
                        filteredLista.add(i)
                    }
                }

            } else if (listaPelis.size>0){
                filteredLista = miDAO.cargarLista(this)
            }
            if (filteredLista.isEmpty()) {
                if (peli0.isNotEmpty()) {
                    Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                } else {
                    if (!listaVacia)
                        filteredLista = miDAO.cargarLista(this)
                }
                adapter.setFilteredLista(filteredLista)
                listaPelis = filteredLista
                binding.rvPelis.adapter = PeliculaAdapter(listaPelis) { pelicula ->
                    onItemSelected(pelicula)
                }
            } else {
                adapter.setFilteredLista(filteredLista)
                listaPelis = filteredLista
                binding.rvPelis.adapter = PeliculaAdapter(listaPelis) { pelicula ->
                    onItemSelected(pelicula)
                }
            }
        }
    }

    private fun onItemSelected(pelicula: Pelicula) {
        val intent = Intent(this, DetallePeliculaActivity::class.java)
        intent.putExtra("pelicula", pelicula)
        startActivity(intent)

    }

    private fun cargarLista(): MutableList<Pelicula> {
        val lista = mutableListOf<Pelicula>()
        lista.addAll(PeliculaProvider.listaPelis)
        return lista
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val buscar = menu?.findItem(R.id.menu_buscar)
        val searchView = buscar?.actionView as SearchView
        searchView.setOnQueryTextListener(object: OnQueryTextListener, SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_buscar -> {
                val searchView = binding.searchView
                if (searchView.visibility == View.GONE) {
                    searchView.visibility = View.VISIBLE
                } else {
                    searchView.visibility = View.GONE
                }
                true
            }
            R.id.menu_limpiar -> {
                confirmarBorrarTodas()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSwipeRefresh() {
        binding.main.setOnRefreshListener {
            listaPelis.clear()
            listaPelis.addAll(cargarLista())
            adapter.notifyDataSetChanged()
            binding.main.isRefreshing = false
        }
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun confirmarBorrarTodas() {
        val alert = AlertDialog.Builder(this).setTitle("Eliminar todas las películas")
            .setMessage("¿Estás seguro de eliminar todas las películas?")
            .setNeutralButton("Cancelar", null)
            .setPositiveButton("Aceptar") { _, _ ->
                listaPelis.clear()
                adapter.notifyDataSetChanged()
            }.create()
        alert.show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val peliSeleccionada: Pelicula = listaPelis[item.groupId]
        when (item.itemId) {
            0 -> {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Eliminar ${peliSeleccionada.titulo}")
                    .setMessage("¿Estás seguro de eliminar ${peliSeleccionada.titulo}?")
                    .setNeutralButton("Cancelar", null)
                    .setPositiveButton("Aceptar") { _, _ ->
                        display("${peliSeleccionada.titulo} eliminada")
                        listaPelis.removeAt(item.groupId)
                        adapter.notifyItemRemoved(item.groupId)
                        adapter.notifyItemRangeChanged(item.groupId, listaPelis.size)
                    }.create()
                alert.show()
            }
            1 -> {
                posicion = item.groupId
                val intent = Intent(this, EditarNombrePeliculaActivity::class.java)
                intent.putExtra("nombrePeli", peliSeleccionada.titulo)
                intent.putExtra("portadaPeli", peliSeleccionada.imagen)
                intentLaunch.launch(intent)
            }

            2 -> {
                posicion = item.groupId
                val intent = Intent(this, CinesActivity::class.java)
                intent.putExtra("pelicula", peliSeleccionada)
                startActivity(intent)
            }

            3 -> {
                val intent = Intent(this, FotoActivity::class.java)
                intent.putExtra("pelicula", peliSeleccionada.titulo)
                intent.putExtra("id", peliSeleccionada.id)
                this.startActivity(intent)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun obtenerCinesAleatorios(): List<Cine> {
        val cinesAleatorios = mutableListOf<Cine>()
        val cinesDisponibles = listaCines.shuffled()
        for (i in 0 until 5) {
            cinesAleatorios.add(cinesDisponibles[i])
        }
        return cinesAleatorios
    }

    private fun asignarCinesAleatorios() {
        for (pelicula in listaPelis) {
            val cinesAleatorios = obtenerCinesAleatorios()
            pelicula.cines = cinesAleatorios
        }
    }




}
