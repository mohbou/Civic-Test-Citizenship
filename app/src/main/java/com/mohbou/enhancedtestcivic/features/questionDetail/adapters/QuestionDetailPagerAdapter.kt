package com.mohbou.enhancedtestcivic.features.questionDetail.adapters

import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.questionDetail.fragment.QuestionDetailFragment

class QuestionDetailPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    var listItems:List<Question>? = null
        set(list)  {
            field = list
            notifyDataSetChanged()
        }
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        val question:Question = listItems!![position]
        return QuestionDetailFragment.newInstance(question.id)
    }

    override fun getCount(): Int {
        return listItems?.size ?: 0
    }

}