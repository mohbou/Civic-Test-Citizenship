package com.mohbou.enhancedtestcivic.features.home.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.application.QuestionApplication
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.features.home.adapters.HomeAdapter
import com.mohbou.enhancedtestcivic.features.home.viewmodel.QuestionListViewModel
import kotlinx.android.synthetic.main.fragment_list_questions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class QuestionListFragment : androidx.fragment.app.Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private var homeAdapter:HomeAdapter? =null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: QuestionListViewModel

    private var job: Job?=null

    private lateinit var menu:Menu


    init {
        QuestionApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        subscribeForUpdateQuestionReview()
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.question_list_menu,menu)
        this.menu = menu

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.review_menu_item -> {
                viewModel.toggleReview()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun subscribeForUpdateQuestionReview() {

        homeAdapter?.questionReviewedClicked?.observe(this, Observer {
            job =   GlobalScope.launch(Dispatchers.Main) {
                viewModel.updateQuestionReview(it)
            }
        })

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
            is QuestionListViewModel.ViewState.ShowErrorMessage -> showMessage(viewState.title, viewState.message)
            is QuestionListViewModel.ViewState.OnToggleReview -> toggleIconReview(viewState.review)
            is QuestionListViewModel.ViewState.ShowUpdateMessage -> showMessage(viewState.title,viewState.message)

        }
    }

    private fun toggleIconReview(review: Boolean) {
        val menuItem = menu.findItem(R.id.review_menu_item)
        if(review) {
            menuItem.setIcon(R.drawable.ic_review_selected)
        }
        else
        {
            menuItem.setIcon(R.drawable.ic_review_unselected)
        }
    }

    private fun showMessage(@StringRes title: Int, @StringRes message: Int) {
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
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        homeRecyclerView.layoutManager = layoutManager
        homeRecyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
        homeAdapter = HomeAdapter(activity!!.applicationContext)
        homeRecyclerView.adapter = homeAdapter

    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        job?.cancel()
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
         * @return A new instance of fragment QuestionListFragment.
         */

        @JvmStatic
        fun newInstance() =QuestionListFragment()
    }
}
