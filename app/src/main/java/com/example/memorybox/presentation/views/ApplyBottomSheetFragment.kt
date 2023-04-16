package com.example.memorybox.presentation.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.FragmentApplyBottomSheetBinding
import com.example.memorybox.databinding.FragmentLoginBinding
import com.example.memorybox.presentation.viewmodel.ApplyBottomSheetViewModel
import com.example.memorybox.presentation.viewmodel.ApplyBottomSheetViewModelFactory
import com.example.memorybox.presentation.viewmodel.LoginViewModel
import com.example.memorybox.presentation.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApplyBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApplyBottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var applyBottomSheetViewModel: ApplyBottomSheetViewModel
    private lateinit var binding: FragmentApplyBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)

        applyBottomSheetViewModel = ViewModelProvider(this, ApplyBottomSheetViewModelFactory(repository)).get(
            ApplyBottomSheetViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val data =  arguments?.getString("teamId")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_apply_bottom_sheet, container, false)


        binding.submitBtn.setOnClickListener {
            applyBottomSheetViewModel.addRequest(data.toString(), applyBottomSheetViewModel.getUser()?.displayName.toString())

        }
        return binding.root
    }



}