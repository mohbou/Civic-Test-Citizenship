package com.mohbou.enhancedtestcivic.features.questionDetail.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.questionDetail.fragment.QuestionDetailFragment

class QuestionDetailPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    var listItems:List<Question>? = null
        set(list)  {
            field = list
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        val question:Question = listItems!![position]
        return QuestionDetailFragment.newInstance(question.id)
    }

    override fun getCount(): Int {
        return listItems?.size ?: 0
    }
}