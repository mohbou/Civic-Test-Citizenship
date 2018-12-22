package com.mohbou.enhancedtestcivic.features.questionDetail.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mohbou.enhancedtestcivic.domain.Answer
import kotlinx.android.synthetic.main.answer_list_item.view.*

class QuestionDetailViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val answerStatement = view.answer_text
    private var answer:Answer? =null

    fun setData(answer: Answer?) {
        this.answer =answer
        answerStatement.text = answer?.answer
    }
}