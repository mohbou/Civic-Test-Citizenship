package com.mohbou.enhancedtestcivic.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
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
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}