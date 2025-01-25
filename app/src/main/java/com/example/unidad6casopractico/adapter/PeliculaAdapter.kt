package com.example.unidad6casopractico.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unidad6casopractico.R
import com.example.unidad6casopractico.model.Pelicula

class PeliculaAdapter(
    private var pelisLista: List<Pelicula>,
    private val onClickListener: (Pelicula) -> Unit
) : RecyclerView.Adapter<PelisViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PelisViewHolder(layoutInflater.inflate(R.layout.item_pelicula, parent, false))
    }

    override fun getItemCount(): Int {
        return pelisLista.size
    }

    override fun onBindViewHolder(holder: PelisViewHolder, position: Int) {
        val item = pelisLista[position]
        holder.render(item, onClickListener)
    }

    fun setFilteredLista(filteredLista: MutableList<Pelicula>) {
        notifyItemRangeRemoved(0, filteredLista.size)
        this.pelisLista = filteredLista
        notifyItemRangeInserted(0, filteredLista.size)
    }
}
