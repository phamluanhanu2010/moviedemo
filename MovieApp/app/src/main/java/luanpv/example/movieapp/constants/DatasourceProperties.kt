package com.pgbank.personal.constants

object DatasourceProperties {
    fun getUrl(): String {
        return SERVER_URL
    }

    const val SERVER_URL = "https://www.omdbapi.com/"
    const val TIMEOUT_CONNECT: Long = 60
    const val TIMEOUT_READ: Long = 300
}

