package com.mohbou.enhancedtestcivic.features.home.viewmodel

import android.content.Intent
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.common.IntentFactory
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.domain.Question
import javax.inject.Inject

class QuestionListViewModel @Inject constructor(private val questionRepository: QuestionRepository,private val intentFactory: IntentFactory):ViewModel(),LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show:Boolean):ViewState()
        class ShowErrorMessage(@StringRes val title:Int,@StringRes val message:Int):ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int):ViewState()
        class OnToggleReview(val review:Boolean):ViewState()

    }

    val viewState:MutableLiveData<ViewState> = MutableLiveData()
    private val showReviewQuestion = MutableLiveData<Boolean>()
    private val questions = MediatorLiveData<List<Question>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        showReviewQuestion.value = false
        viewState.value = ViewState.UpdateScreenTitle(R.string.app_name)
        val liveCurrentQuestion = Transformations.switchMap(showReviewQuestion) {
            if(it) {
                questionRepository.getAllQuestionstoReview()
            } else{
                questionRepository.getAllQuestions()
            }
        }
        questions.addSource(liveCurrentQuestion,questions::setValue)
    }

    fun getAllQuestions(): LiveData<List<Question>>? = questions

    suspend fun updateQuestionReview(question: Question) {
        questionRepository.updateQuestionReview(question)
    }


    fun intentForQuestionDetailActivity(questionID: String?): Intent {
        return intentFactory.intentForQuestionDetailActivity(questionID)
    }

    fun toggleReview() {
        showReviewQuestion.value = !shouldShowReviewQuestion()
        viewState.value = ViewState.OnToggleReview(showReviewQuestion.value!!)
    }

    private fun shouldShowReviewQuestion() = showReviewQuestion.value ==true
}