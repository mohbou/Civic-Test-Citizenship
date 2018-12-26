package com.mohbou.enhancedtestcivic.di

import android.content.Context
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.features.home.fragment.QuestionListFragment
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import com.mohbou.enhancedtestcivic.features.questionDetail.fragment.QuestionDetailPagerFragment
import com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel.QuestionDetailViewModel
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class, DataModule::class,ViewModelModule::class])
interface AppComponent {

    fun inject(questionListQuestionFragment: QuestionListFragment)
    fun inject(questionListViewModel: QuestionListViewModel)
    fun inject(questionRepository: QuestionRepository)
    fun inject(networkRepository: NetworkRepository)
    fun inject(questionDetailPagerFragment: QuestionDetailPagerFragment)
    fun inject(questionDetailViewModel: QuestionDetailViewModel)
    fun inject(civicTestDatabase: CivicTestDatabase)


}