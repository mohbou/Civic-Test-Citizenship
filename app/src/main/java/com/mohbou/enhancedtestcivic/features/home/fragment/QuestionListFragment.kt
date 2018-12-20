package com.mohbou.enhancedtestcivic.features.home.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.application.QuestionApplication
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.home.adapters.HomeAdapter
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import kotlinx.android.synthetic.main.fragment_list_questions.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var homeAdapter:HomeAdapter? =null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: QuestionListViewModel


    init {
        QuestionApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_questions, container, false)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(QuestionListViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        subscribeForViewStateChange()
        subscribeForQuestionList()
        subscribeForQuestionDetail()
        lifecycle.addObserver(viewModel)
    }

    @SuppressLint("CheckResult")
    private fun subscribeForQuestionDetail() {
        homeAdapter?.questionDetailClickedSubject?.subscribe{
            startActivity(viewModel.intentForQuestionDetailActivity(it.id))
        }
    }

    private fun subscribeForQuestionList() {
        viewModel.getAllQuestions()?.observe(this, Observer {
            setAdapterItems(it)
        })

    }

    private fun setAdapterItems(list: List<Question>?) {
        homeAdapter?.listItems = list
    }

    private fun subscribeForViewStateChange() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(viewState: QuestionListViewModel.ViewState?) {
        when (viewState) {
            is QuestionListViewModel.ViewState.UpdateScreenTitle -> activity!!.setTitle(viewState.screenTitle)
            is QuestionListViewModel.ViewState.ShowLoading -> showLoading(viewState.show)
            is QuestionListViewModel.ViewState.ShowErrorMessage -> showErrorMessage(viewState.title, viewState.message)

        }
    }

    private fun showErrorMessage(@StringRes title: Int, @StringRes message: Int) {
        Snackbar.make(question_list_fragment_root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {

        if (show) {
            progressBar_question.visibility = View.VISIBLE
        } else {
            progressBar_question.visibility = View.GONE
        }

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        homeRecyclerView.layoutManager = layoutManager
        homeRecyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        homeAdapter = HomeAdapter(activity!!.applicationContext)
        homeRecyclerView.adapter = homeAdapter

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
