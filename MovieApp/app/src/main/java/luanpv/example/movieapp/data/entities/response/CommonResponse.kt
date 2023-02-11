package luanpv.example.movieapp.data.entities.response

import com.google.gson.annotations.SerializedName


data class MovieItemResponse(
    @SerializedName("Title") val Title: String?,
    @SerializedName("Year") val Year: String?,
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Type") val Type: String?,
    @SerializedName("Poster") val Poster: String?
) {
   
}


