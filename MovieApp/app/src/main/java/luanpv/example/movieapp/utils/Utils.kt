package luanpv.example.movieapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder


class Utils {
    companion object {
        private var instance: Utils? = null
        @JvmStatic
        fun g(): Utils {
            if (instance == null)
                instance = Utils()
            return instance!!
        }
    }

    private var gson: Gson? = null
    fun provideGson(): Gson {
        if (gson == null)
            gson = GsonBuilder().create()
        return gson!!
    }
}