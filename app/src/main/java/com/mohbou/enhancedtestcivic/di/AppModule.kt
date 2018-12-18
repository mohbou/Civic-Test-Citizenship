package com.mohbou.enhancedtestcivic.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Context) {

    @ApplicationScope
    @Provides
    fun providesApplication(): Context {
        return application
    }
}