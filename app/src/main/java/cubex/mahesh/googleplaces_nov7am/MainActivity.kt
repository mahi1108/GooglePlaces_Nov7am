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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SeekBar
import com.google.android.gms.location.places.ui.PlacePicker
import cubex.mahesh.googleplaces_nov7am.beans.PlacesBean
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiview.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        sbar1.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        progress.setText(p1.toString())
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }

                })

        getPlaces.setOnClickListener {

            var r = Retrofit.Builder().
                            addConverterFactory(GsonConverterFactory.create()).
                            baseUrl("https://maps.googleapis.com/").
                            build()
            var api = r.create(PlacesAPI::class.java)
            var call = api.getPlaces("${tv_lati.text},${tv_longi.text}",
                                                sp1.selectedItem.toString(),
                                                progress.text.toString())
            call.enqueue(object :Callback<PlacesBean> {

                override fun onResponse(call: Call<PlacesBean>, response: Response<PlacesBean>) {

                    var bean = response.body()
                   var list =  bean!!.results
                    lview.adapter = object:BaseAdapter(){
                        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                            var inflater = LayoutInflater.from(this@MainActivity)
                            var v = inflater.inflate(R.layout.indiview,null)
                            v.pname.text = list!!.get(p0).name
                            v.paddress.text = list!!.get(p0).vicinity
                            v.pohours.text = list!!.get(p0).openingHours.openNow.toString()
                            return  v
                        }

                        override fun getItem(p0: Int): Any {
                        return  0
                        }

                        override fun getItemId(p0: Int): Long {
                       return  0
                        }

                        override fun getCount(): Int {
                            return  list!!.size
                        }


                    }

                }

                override fun onFailure(call: Call<PlacesBean>, t: Throwable) {

                }

            })

        }



    } // onCreate( )

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
