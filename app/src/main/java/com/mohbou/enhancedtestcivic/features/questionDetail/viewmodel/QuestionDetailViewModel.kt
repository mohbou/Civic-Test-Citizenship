package com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel

import android.arch.lifecycle.*
import android.support.annotation.StringRes
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class QuestionDetailViewModel @Inject constructor(private val questionRepository: QuestionRepository):ViewModel(),LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show:Boolean): ViewState()
        class ShowErrorMessage(@StringRes val title:Int, @StringRes val message:Int): ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int): ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val subscription= CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.question_detail)
    }

    fun getAllQuestions(): LiveData<List<Question>>? = questionRepository.getAllQuestions()

    fun getIndexQuestionById(questionId: UUID):LiveData<Int> {
        return questionRepository.getIndexQuestionById(questionId)

    }

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}