package com.mohbou.enhancedtestcivic.features.home.viewmodel

import android.arch.lifecycle.*
import android.content.Intent
import android.support.annotation.StringRes
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.common.IntentFactory
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.domain.Question
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class QuestionListViewModel @Inject constructor(private val questionRepository: QuestionRepository,private val intentFactory: IntentFactory):ViewModel(),LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show:Boolean):ViewState()
        class ShowErrorMessage(@StringRes val title:Int,@StringRes val message:Int):ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int):ViewState()

    }

    val viewState:MutableLiveData<ViewState> = MutableLiveData()
    private val subscription=CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.app_name)
    }

    fun getAllQuestions(): LiveData<List<Question>>? = questionRepository.getAllQuestions()

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }



    fun intentForQuestionDetailActivity(questionID: UUID?): Intent {
        return intentFactory.intentForQuestionDetailActivity(questionID)
    }
}