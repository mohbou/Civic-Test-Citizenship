package com.mohbou.enhancedtestcivic.features.questionDetail.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.application.QuestionApplication
import com.mohbou.enhancedtestcivic.common.Constants
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.questionDetail.adapters.QuestionDetailPagerAdapter
import com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel.QuestionDetailViewModel
import kotlinx.android.synthetic.main.fragment_question_detail_pager.*
import javax.inject.Inject


class QuestionDetailPagerFragment : Fragment() {

    //id we receive from main Question List to define where we start in the ViewPager
    private var questionId: String? = null

    private var questionDetailPagerAdapter:QuestionDetailPagerAdapter?=null

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel:QuestionDetailViewModel

    private var currentItem:Boolean = false


    init {
        QuestionApplication.appComponent.inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionId = it.getString(Constants.QUESTION_ID)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_question_detail_pager, container, false)
        viewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(QuestionDetailViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewPager()
        subscribeForAllQuestion()
        subscribeForViewStateChange()
        lifecycle.addObserver(viewModel)

    }

    private fun subscribeForViewStateChange() {
        viewModel.viewState.observe(this,Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(viewState: QuestionDetailViewModel.ViewState?) {
        when(viewState) {
            is QuestionDetailViewModel.ViewState.UpdateScreenTitle -> activity?.setTitle(viewState.screenTitle)
            is QuestionDetailViewModel.ViewState.ShowLoading -> showLoading(viewState.show)
            is QuestionDetailViewModel.ViewState.ShowErrorMessage -> showErrorMessage(viewState.title,viewState.message)

        }
    }

    private fun showErrorMessage(@StringRes title: Int,@StringRes message: Int) {
        Snackbar.make(question_detail_fragment, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            progress_bar_pager.visibility = View.VISIBLE
        } else {
            progress_bar_pager.visibility = View.GONE
        }
    }

    private fun subscribeForAllQuestion() {
        viewModel.getAllQuestions()?.observe(this, Observer {
            setAdapterItems(it)
        })
    }

    private fun setAdapterItems(questionList: List<Question>?) {
        questionDetailPagerAdapter?.listItems=questionList
       if(!currentItem) {
           questionList?.forEachIndexed { index, question ->
               if (question.id == questionId)
                   question_detail_pager.currentItem = index
                   currentItem = true

           }
       }
    }

    private fun setupViewPager() {
        questionDetailPagerAdapter = QuestionDetailPagerAdapter(activity?.supportFragmentManager!!)
        question_detail_pager.adapter = questionDetailPagerAdapter



    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
         fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param questionId Parameter 1.
         * @return A new instance of fragment QuestionDetailPagerFragment.
         */

        @JvmStatic
        fun newInstance(questionId: String) =
            QuestionDetailPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.QUESTION_ID, questionId)

                }
            }
    }
}
