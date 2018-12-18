package com.mohbou.enhancedtestcivic.di

import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideQuestionRepository(networkRepository: NetworkRepository): QuestionRepository {
        return QuestionRepository(networkRepository)
    }

    @ApplicationScope
    @Provides
    fun provideNetworkRepository(): NetworkRepository {
        return NetworkRepository()
    }

}