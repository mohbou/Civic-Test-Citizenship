package com.mohbou.enhancedtestcivic.di

import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.features.home.fragment.QuestionListFragment
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class, DataModule::class,ViewModelModule::class])
interface AppComponent {

    fun inject(questionListQuestionFragment: QuestionListFragment)
    fun inject(questionListViewModel: QuestionListViewModel)
    fun inject(questionRepository: QuestionRepository)
    fun inject(networkRepository: NetworkRepository)


}