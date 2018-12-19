package com.mohbou.enhancedtestcivic.features.home.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.home.viewholder.QuestionListViewHolder
import io.reactivex.subjects.PublishSubject

class HomeAdapter(val context: Context):RecyclerView.Adapter<QuestionListViewHolder>() {

    var listItems: List<Question>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }


  // publish subject questionDetailClickedSubject to serve as Obeservable when user click on question and want to see the Answers
 val questionDetailClickedSubject=PublishSubject.create<Question>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListViewHolder {
        return QuestionListViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list_item,parent,false),questionDetailClickedSubject)
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: QuestionListViewHolder, position: Int) {
        holder.setData(listItems?.get(position))
    }
}
