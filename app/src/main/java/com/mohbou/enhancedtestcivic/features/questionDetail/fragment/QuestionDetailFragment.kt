package com.mohbou.enhancedtestcivic.features.questionDetail.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.application.QuestionApplication
import com.mohbou.enhancedtestcivic.common.Constants


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionDetailFragment : Fragment() {

    private var questionId: String? = null

    private var listener: OnFragmentInteractionListener? = null

    init {
        QuestionApplication.appComponent.inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionId = it.getString(Constants.QUESTION_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_detail, container, false)
    }

   
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
         * @param questionId Parameter 1.
         * @return A new instance of fragment QuestionDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(questionId: String) =
            QuestionDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.QUESTION_ID, questionId)

                }
            }
    }
}
