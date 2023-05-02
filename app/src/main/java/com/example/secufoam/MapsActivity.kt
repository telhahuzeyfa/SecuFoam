package com.example.secufoam
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import org.jetbrains.anko.doAsync
import java.util.*

// Start of highlighted code
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


// Define the center point of the two circles
        val center = LatLng(38.8985 + 0.002, -77.0465)

// Define the radius and number of points for the circles
        val radius = 0.005
        val points = 50

// Define the coordinates for the red circle
        val polygonCoords = (0 until points).map {
            val angle = it.toDouble() / points.toDouble() * 2.0 * Math.PI
            LatLng(center.latitude + radius * Math.sin(angle), center.longitude + radius * Math.cos(angle) - 0.001)
        }

// Add the red circle to the map
        val polygon = mMap.addPolygon(PolygonOptions().addAll(polygonCoords))
        polygon.fillColor = Color.argb(128, 255, 64, 64)
        polygon.strokeWidth = 0f

// Define the coordinates for the green circle
        val polygonCoords1 = (0 until points).map {
            val angle = it.toDouble() / points.toDouble() * 2.0 * Math.PI
            LatLng(center.latitude + radius * Math.sin(angle), center.longitude + radius * Math.cos(angle) + 0.001)
        }

// Add the green circle to the map
        val polygon1 = mMap.addPolygon(PolygonOptions().addAll(polygonCoords1))
        polygon1.fillColor = Color.argb(128, 64, 255, 64)
        polygon1.strokeWidth = 0f



        // Define the coordinates of George Washington University
        val gwuLatLng = LatLng(38.899176, -77.047092)

        // Add a marker for George Washington University
        mMap.addMarker(MarkerOptions().position(gwuLatLng).title("George Washington University").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))


        // Zoom the camera to George Washington University
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gwuLatLng, 15f))

        // Define the colors
        val GREEN = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        val YELLOW = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
        val RED = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

        // Add markers with random coordinates and colors
        val random = Random()
        val boundsBuilder = LatLngBounds.Builder()
        for (i in 0 until 10) {
            val lat = 38.898 + random.nextDouble() * 0.005
            val lng = -77.049 + random.nextDouble() * 0.005
            val color = when (random.nextInt(3)) {
                0 -> GREEN
                1 -> YELLOW
                else -> RED
            }
            val percentage = random.nextInt(31) + when (color) {
                GREEN -> 70
                YELLOW -> 30
                else -> 0
            }
            val title = when (color) {
                GREEN -> "Dispenser $i is full ($percentage%)"
                YELLOW -> "Warning: Dispenser $i ($percentage%)"
                else -> "Dispenser $i is empty ($percentage%)"
            }
            mMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).icon(color).title(title))
            val latLng = LatLng(lat, lng)
            boundsBuilder.include(latLng)
        }
        // Build the bounds of all the markers
        val bounds = boundsBuilder.build()

        // Set a padding to offset the camera from the edge of the screen
        val padding = resources.getDimensionPixelSize(R.dimen.map_padding)

        // Animate the camera to show all the markers
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.animateCamera(cameraUpdate)
    }
}