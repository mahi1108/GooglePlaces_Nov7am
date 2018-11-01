package cubex.mahesh.googleplaces_nov7am

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentLocation( )

        pin.setOnClickListener {
            var i = PlacePicker.IntentBuilder( )
            startActivityForResult(i.build(this@MainActivity),
                    123)
        }

    }

    @SuppressLint("MissingPermission")
    fun  getCurrentLocation( )
    {
         var lManager = getSystemService(Context.LOCATION_SERVICE)
                 as LocationManager
        lManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000.toLong(), 1.toFloat(),
                object : LocationListener {
                    override fun onLocationChanged(l: Location?) {
                        tv_lati.setText(l!!.latitude.toString())
                        tv_longi.setText(l!!.longitude.toString())
                        lManager.removeUpdates(this)
                    }
                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                    }
                    override fun onProviderEnabled(p0: String?) {
                    }
                    override fun onProviderDisabled(p0: String?) {

                    }
                })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK)
        {
            var place = PlacePicker.getPlace(this@MainActivity,data)
            tv_lati.setText(place.latLng.latitude.toString())
            tv_longi.setText(place.latLng.longitude.toString())

        }

    }

}
