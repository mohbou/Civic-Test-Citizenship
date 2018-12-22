package com.mohbou.enhancedtestcivic.di

import android.content.Context
import com.google.gson.Gson
import com.mohbou.enhancedtestcivic.common.IntentFactory
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import dagger.Module
import dagger.Provides
import java.io.InputStream

@Module(includes=[AppModule::class])
class DataModule {

    @ApplicationScope
    @Provides
    fun provideQuestionRepository(networkRepository: NetworkRepository): QuestionRepository {
        return QuestionRepository(networkRepository)
    }

    @ApplicationScope
    @Provides
    fun provideNetworkRepository(inputStream: InputStream,gson:Gson): NetworkRepository {
        return NetworkRepository(inputStream,gson)
    }

    @ApplicationScope
    @Provides
    fun provideInputStream(context: Context): InputStream {
        return context.resources.openRawResource(context.resources.getIdentifier("test_civic_question", "raw", context.packageName))
    }

    @ApplicationScope
    @Provides
    fun provideIntentFactory(application: Context): IntentFactory {
        return IntentFactory(application)
    }

    @ApplicationScope
    @Provides
    fun provideGson():Gson {
        return Gson()
    }

}