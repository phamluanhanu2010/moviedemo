package com.pgbank.personal.data.entities

import com.google.gson.annotations.SerializedName
import luanpv.example.movieapp.utils.Utils
import java.util.*


data class BaseDataRequest(
    @SerializedName("reqBody") val reqBody: String,
)

open class TemplateChildRequest {
    @SerializedName("osType")
    var osType: String = "AND"

    fun build(): BaseDataRequest {
        return BaseDataRequest(reqBody = Utils.g().provideGson().toJson(this))
    }
}
