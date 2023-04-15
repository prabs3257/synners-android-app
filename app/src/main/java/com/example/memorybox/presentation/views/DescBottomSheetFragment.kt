package com.example.memorybox.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.memorybox.databinding.FragmentDescBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DescBottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentDescBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater,
            com.example.memorybox.R.layout.fragment_desc_bottom_sheet, container, false)

        binding.creatorTv.text = "Memory Created by ${arguments!!.getString("creatorName")}"
        binding.descTv.text = arguments!!.getString("desc")

        Glide.with(this).load(arguments!!.getString("imgLink")).centerCrop().into(binding.uploadImgIv)
        return binding.root
    }



}