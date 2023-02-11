package luanpv.example.movieapp.data.entities.response

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import luanpv.example.movieapp.utils.Utils

data class CommonJSONResponse(
    @SerializedName("Response") val Response: String?,
    @SerializedName("Error") val Error: String?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Search") val Search: ArrayList<MovieItemResponse>?,
) {

}



