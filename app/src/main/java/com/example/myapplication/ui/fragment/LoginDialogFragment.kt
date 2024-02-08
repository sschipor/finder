package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginDialogBinding
import com.example.myapplication.ui.alert.AlertDialogUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginDialogFragment : DialogFragment() {

    private var binding: FragmentLoginDialogBinding? = null
    private val viewModel: LoginFragmentViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        //make dialog full screen
        dialog?.window?.setLayout(
            /* width = */ ViewGroup.LayoutParams.MATCH_PARENT,
            /* height = */ ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginDialogBinding.inflate(inflater)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        with(viewModel) {
            onLoginSuccess.observeEvent(viewLifecycleOwner) {
                if (it) dismiss()
            }
            onLoginError.observeEvent(viewLifecycleOwner) {
                AlertDialogUtil.showAlertDialog(
                    context = requireActivity(),
                    title = getString(R.string.login_error_title),
                    body = getString(R.string.error_general_body),
                    okButton = getString(R.string.ok_button)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val TAG = LoginDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = LoginDialogFragment()
    }
}