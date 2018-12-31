package com.mohbou.enhancedtestcivic.features.questionDetail.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.common.Constants
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.features.questionDetail.adapters.QuestionDetailAdapter
import com.mohbou.enhancedtestcivic.features.questionDetail.viewmodel.QuestionDetailViewModel
import kotlinx.android.synthetic.main.fragment_question_detail.*
import javax.inject.Inject


class QuestionDetailFragment : Fragment() {

    private var questionId: String? = null

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: QuestionDetailViewModel

    private var questionDetailAdapter: QuestionDetailAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionId = it.getString(Constants.QUESTION_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_detail, container, false)
        viewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(QuestionDetailViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        subscribeForAnswerList()

    }

    private fun subscribeForAnswerList() {
        viewModel.getQuestionById(questionId).observe(this,Observer {
          question->  question?.let { setAdapterItems(question.answers) }


        })
    }
    private fun setAdapterItems(list: List<Answer>?) {
        questionDetailAdapter?.listItems = list
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        recycler_view_answer.layoutManager = layoutManager
        recycler_view_answer.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
        questionDetailAdapter = QuestionDetailAdapter(activity!!.applicationContext)
        recycler_view_answer.adapter = questionDetailAdapter

    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(questionID: String) =
            QuestionDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.QUESTION_ID, questionID)

                }
            }
    }
}
