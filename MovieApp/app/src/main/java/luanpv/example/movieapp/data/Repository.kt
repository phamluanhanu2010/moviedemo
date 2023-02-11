package com.pgbank.personal.data

import com.pgbank.personal.data.entities.request.*
import luanpv.example.movieapp.data.entities.response.CommonJSONResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val GET_MOVIE_BY_NAME = "?apikey=b9bd48a6&type=movie"

interface Service {

    @GET(GET_MOVIE_BY_NAME)
    suspend fun searchByName(
        @Query("s") name: String, @Query("page") page: Int
    ): CommonJSONResponse

}