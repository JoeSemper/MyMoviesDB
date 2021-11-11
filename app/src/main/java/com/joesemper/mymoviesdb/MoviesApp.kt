package com.joesemper.mymoviesdb

import android.app.Application
import com.joesemper.mymoviesdb.di.appModule
import com.joesemper.mymoviesdb.di.mainActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(
                listOf(
                    appModule,
                    mainActivity
                )
            )
        }
    }
}