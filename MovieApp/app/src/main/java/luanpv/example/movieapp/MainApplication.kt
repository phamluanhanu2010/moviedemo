package luanpv.example.movieapp

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : MultiDexApplication() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(com.pgbank.personal.di.modules)
        }
    }

    companion object {
        private var instance: MainApplication? = null

        fun getInstanceApp(): MainApplication {
            return instance as MainApplication
        }
    }

}
