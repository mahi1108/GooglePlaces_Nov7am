package cubex.mahesh.googleplaces_nov7am

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var from = intent.getBooleanExtra("from_single",
                false)
        if(from) {
            var cur_lati = intent.getDoubleExtra("cur_loc_lati",
                    0.0)
            var cur_long = intent.getDoubleExtra("cur_loc_long",
                    0.0)
            var dest_lati = intent.getDoubleExtra("dest_loc_lati",
                    0.0)
            var dest_longi = intent.getDoubleExtra("dest_loc_long",
                    0.0)

            var options = MarkerOptions( )
            options.position(LatLng(cur_lati,cur_long))
            options.icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN))
            googleMap.addMarker(options)
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        LatLng(cur_lati,cur_long),15.0f))

            var options1 = MarkerOptions( )
            options1.position(LatLng(dest_lati,dest_longi))
            googleMap.addMarker(options1)

        }else{
            var cur_lati = intent.getDoubleExtra("cur_loc_lati",
                    0.0)
            var cur_long = intent.getDoubleExtra("cur_loc_long",
                    0.0)
            var options = MarkerOptions( )
            options.position(LatLng(cur_lati,cur_long))
            options.icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN))
            googleMap.addMarker(options)
            googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            LatLng(cur_lati,cur_long),15.0f))
            for(place in list!!)
            {
                var options1 = MarkerOptions( )
                options1.position(LatLng(place.geometry.location.lat,
                        place.geometry.location.lng))
                options1.title(place.name)
                googleMap.addMarker(options1)
            }


        }

    }
}
