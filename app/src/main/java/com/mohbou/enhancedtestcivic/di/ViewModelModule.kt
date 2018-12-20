package com.mohbou.enhancedtestcivic.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel.QuestionDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuestionListViewModel::class)
    internal abstract fun bindQuestionListViewModel(questionListViewModel: QuestionListViewModel): ViewModel




    @Binds
    @IntoMap
    @ViewModelKey(QuestionDetailViewModel::class)
    internal abstract fun bindQuestionDetailViewModel(questionDetailViewModel: QuestionDetailViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}