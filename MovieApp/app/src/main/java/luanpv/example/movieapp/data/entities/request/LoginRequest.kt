package com.pgbank.personal.data.entities.request

import com.google.gson.annotations.SerializedName
import com.pgbank.personal.data.entities.TemplateChildRequest


data class GetMovies(
    @SerializedName("name") val name: String?

) : TemplateChildRequest()


