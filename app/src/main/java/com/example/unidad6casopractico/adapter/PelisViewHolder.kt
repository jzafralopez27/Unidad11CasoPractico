package com.example.unidad6casopractico.adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.unidad6casopractico.databinding.ItemPeliculaBinding
import com.example.unidad6casopractico.model.Pelicula

class PelisViewHolder(view: View) : ViewHolder(view), View.OnCreateContextMenuListener {

    private val binding = ItemPeliculaBinding.bind(view)

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    fun render(item: Pelicula, onClick: (Pelicula) -> Unit) {
        binding.tvTitulo.text = item.titulo
        binding.ivPoster.setImageResource(item.imagen)

        itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        contextMenuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle(binding.tvTitulo.text)
        menu?.add(adapterPosition, 0, 0, "Eliminar")
        menu?.add(adapterPosition, 1, 1, "Editar")
        menu?.add(adapterPosition, 2, 2, "Ver cines")
        menu?.add(adapterPosition, 3, 3, "Hacer foto")
        menu?.add(adapterPosition, 4, 4, "Ver foto")
    }
}
