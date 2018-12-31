package com.mohbou.enhancedtestcivic.features.questionDetail.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.application.QuestionApplication
import com.mohbou.enhancedtestcivic.common.Constants
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.questionDetail.adapters.QuestionDetailPagerAdapter
import com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel.QuestionDetailViewModel
import kotlinx.android.synthetic.main.fragment_question_detail_pager.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject


class QuestionDetailPagerFragment : Fragment() {

    //id we receive from main Question List to define where we start in the ViewPager
    private var questionId: String? = null

    private var questionDetailPagerAdapter:QuestionDetailPagerAdapter?=null

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel:QuestionDetailViewModel
    private val subscriptions = CompositeSubscription()

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showLoading(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun subscribeForAllQuestion() {
        viewModel.getAllQuestions()?.observe(this, Observer {
            setAdapterItems(it)
        })
    }

    private fun setAdapterItems(questionList: List<Question>?) {
        questionDetailPagerAdapter?.listItems=questionList
    }

    private fun setupViewPager() {
        questionDetailPagerAdapter = QuestionDetailPagerAdapter(activity?.supportFragmentManager)
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
        // TODO: Update argument type and name
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
