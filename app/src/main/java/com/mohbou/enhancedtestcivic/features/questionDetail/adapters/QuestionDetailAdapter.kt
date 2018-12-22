package com.mohbou.enhancedtestcivic.features.questionDetail.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.features.questionDetail.viewholder.QuestionDetailViewHolder

class QuestionDetailAdapter(val context:Context):RecyclerView.Adapter<QuestionDetailViewHolder>() {

    var listItems: List<Answer>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionDetailViewHolder {
       return QuestionDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.answer_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return listItems?.size ?:0
    }

    override fun onBindViewHolder(holder: QuestionDetailViewHolder, position: Int) {
        holder?.setData(listItems?.get(position))
    }
}