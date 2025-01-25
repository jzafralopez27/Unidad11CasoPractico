package com.example.unidad6casopractico.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unidad6casopractico.R
import com.example.unidad6casopractico.model.Pelicula
import org.osmdroid.api.IMapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint

class CinesActivity : AppCompatActivity() {

    private lateinit var pelicula: Pelicula
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        pelicula = intent.getSerializableExtra("pelicula") as Pelicula

        mapView = findViewById(R.id.mapview)
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        if (pelicula.cines.isNotEmpty()) {
            var latSum = 0.0
            var lonSum = 0.0
            for (cine in pelicula.cines) {
                latSum += cine.latitud
                lonSum += cine.longitud
            }

            val latCentro = latSum / pelicula.cines.size
            val lonCentro = lonSum / pelicula.cines.size

            val geoPoint = GeoPoint(latCentro, lonCentro)

            val mapController: IMapController = mapView.controller
            mapController.setCenter(geoPoint)
            mapController.setZoom(7)

            for (cine in pelicula.cines) {
                val marker = Marker(mapView)
                val geoPointCine = GeoPoint(cine.latitud, cine.longitud)
                marker.position = geoPointCine
                marker.title = cine.nombre
                mapView.overlays.add(marker)
            }
        }
    }
}
