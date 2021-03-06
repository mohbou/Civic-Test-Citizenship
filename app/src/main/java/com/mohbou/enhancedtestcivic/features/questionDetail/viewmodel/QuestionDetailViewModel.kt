package com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.data.QuestionRepository
import com.mohbou.enhancedtestcivic.domain.Question
import javax.inject.Inject

class QuestionDetailViewModel @Inject constructor(private val questionRepository: QuestionRepository):ViewModel(),LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show:Boolean): ViewState()
        class ShowErrorMessage(@StringRes val title:Int, @StringRes val message:Int): ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int): ViewState()
        class OnReviewToggled(val review:Boolean):ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.question_detail)
    }

    fun getAllQuestions(): LiveData<List<Question>>? = questionRepository.getAllQuestions()

    suspend fun getQuestionById(questionId: String?):LiveData<Question> {
         return questionRepository.getQuestionById(questionId)
    }

    suspend fun updateQuestionReview(questionId: String?,review:Boolean) {

        questionRepository.updateQuestionReviewToggle(questionId,review)
        viewState.value = ViewState.OnReviewToggled(!review)
        }

}