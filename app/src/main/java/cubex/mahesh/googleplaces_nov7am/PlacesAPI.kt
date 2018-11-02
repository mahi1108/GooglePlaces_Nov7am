package cubex.mahesh.googleplaces_nov7am

import cubex.mahesh.googleplaces_nov7am.beans.PlacesBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI
{
    @GET("maps/api/place/nearbysearch/json?key=AIzaSyDdCGdR2cnWw0AB0LeN3jOTjKmkKag4tew")
    fun getPlaces(@Query("location") l:String,
                                @Query("type") t:String,
                                @Query("radius") r:String) : Call<PlacesBean>

}