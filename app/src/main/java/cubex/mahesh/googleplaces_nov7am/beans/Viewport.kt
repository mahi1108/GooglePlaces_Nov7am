package cubex.mahesh.googleplaces_nov7am.beans

import com.google.gson.annotations.SerializedName

data class Viewport(@SerializedName("southwest")
                    val southwest: Southwest,
                    @SerializedName("northeast")
                    val northeast: Northeast)