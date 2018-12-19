package com.mohbou.enhancedtestcivic.di

import android.content.Context
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
    fun provideNetworkRepository(inputStream: InputStream): NetworkRepository {
        return NetworkRepository(inputStream)
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

}