package com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.domain.Question
import javax.inject.Inject

class QuestionDetailItemViewModel @Inject constructor(private val questionRepository: QuestionRepository
                                                      ): ViewModel(),
    LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show:Boolean): ViewState()
        class ShowErrorMessage(@StringRes val title:Int, @StringRes val message:Int): ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int): ViewState()
        class OnReviewToggled(val review:Boolean):ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    var question : LiveData<Question> =MutableLiveData()

    var questionId:String?=null



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.question_detail)


    }

    suspend fun getQuestionById(questionId: String?):LiveData<Question> {
        question = questionRepository.getQuestionById(questionId)
        return question
    }


    suspend fun updateQuestionReview() {
        question.value?.review =!question.value?.review!!
        questionRepository.updateQuestionReviewToggle(questionId,question.value?.review!!)
        viewState.value = ViewState.OnReviewToggled(question.value?.review!!)
    }



}