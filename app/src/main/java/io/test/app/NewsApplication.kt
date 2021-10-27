package io.test.app

import android.app.Application
import io.test.app.dependencies.ServiceLocator

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}