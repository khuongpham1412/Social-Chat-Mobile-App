package com.android.socialchat.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {

        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger()
            // Android context
            androidContext(this@App)
            // modules
            koin.loadModules(arrayListOf(retrofitModule, viewModelModule, repositoryModule, dataModule))
            koin.createRootScope()
        }
    }
}