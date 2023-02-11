package com.pgbank.personal.data.impl

import com.pgbank.personal.data.Service
import luanpv.example.movieapp.data.entities.response.CommonJSONResponse

class SearchRepo(val service: Service) {
    suspend fun searchByName(name: String, page: Int): CommonJSONResponse {
        return service.searchByName(name, page)

    }
}