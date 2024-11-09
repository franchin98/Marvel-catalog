package com.softchin.marvelcatalog

import android.app.Application
import com.softchin.marvelcatalog.di.KoinInitializer

class MarvelApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}