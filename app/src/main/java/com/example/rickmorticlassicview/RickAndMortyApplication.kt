package com.example.rickmorticlassicview

import android.app.Application
import com.example.rickmorticlassicview.di.baseModule
import com.example.rickmorticlassicview.di.charactersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@RickAndMortyApplication)
            modules(listOf(baseModule, charactersModule)).allowOverride(true)
        }
    }
}