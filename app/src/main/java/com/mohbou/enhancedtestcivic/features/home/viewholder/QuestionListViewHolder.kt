package com.mohbou.enhancedtestcivic.features.home.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.domain.Question
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.question_list_item.view.*

class QuestionListViewHolder(view: View, questionDetailClickedSubject:PublishSubject<Question>):RecyclerView.ViewHolder(view) {

    private val questionStatement = view.question_statement
    private val questionToReviewIcon =view.question_toreview
    private var question:Question? =null

    init {
        questionToReviewIcon.setOnClickListener {
            question?.review?.let { review ->
                if(review) {
                  questionToReviewIcon.setImageResource(R.drawable.ic_review_unselected)
                    question!!.review=false
                       }
                else {
                    questionToReviewIcon.setImageResource(R.drawable.ic_review_selected)
                    question!!.review=true
                }
            }
        }

        questionStatement.setOnClickListener {
            questionDetailClickedSubject.onNext(question!!)
        }
    }

    fun setData(questionEntity: Question?) {
        this.question = questionEntity
        questionStatement.text = questionEntity?.question
        if(questionEntity?.review!!) {
            questionToReviewIcon.setImageResource(R.drawable.ic_review_selected)
        }
        else
            questionToReviewIcon.setImageResource(R.drawable.ic_review_unselected)

    }
}
