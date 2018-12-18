package com.mohbou.enhancedtestcivic.application

import android.app.Application
import android.content.Context
import com.mohbou.enhancedtestcivic.di.AppComponent
import com.mohbou.enhancedtestcivic.di.AppModule
import com.mohbou.enhancedtestcivic.di.DaggerAppComponent

class QuestionApplication:Application() {


    companion object {
        lateinit var applicationContext: Context
        lateinit var appComponent:AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        QuestionApplication.applicationContext = this
        appComponent = DaggerAppComponent.builder()
                                         .appModule(AppModule(this))
                                         .build()

    }
}