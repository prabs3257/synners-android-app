package com.example.memorybox.presentation.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.FragmentAddMemoryBinding
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModel
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModelFactory


class AddMemoryFragment : Fragment() {

    private lateinit var binding: FragmentAddMemoryBinding
    private lateinit var imageURI: Uri
    private lateinit var addMemoryViewModel: AddMemoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)
        addMemoryViewModel = ViewModelProvider(this, AddMemoryViewModelFactory(repository)).get(
            AddMemoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_memory, container, false)

        binding.addImageBtn.setOnClickListener {
            selectImg()

        }
        binding.confirmBtn.setOnClickListener {
            addMemoryViewModel.uploadImg(imageURI)
        }

        addMemoryViewModel.getImgLinkLiveData().observe(this.requireActivity(),{
            addMemoryViewModel.addMemory(Memory(desc=binding.descEt.editText?.text.toString()
                , imgLink = it
                , lat = arguments!!.getString("lat")
                ,lng= arguments!!.getString("lng")
            , creatorName = addMemoryViewModel.getUser()!!.displayName
            , creatorImg = addMemoryViewModel.getUser()!!.photoUrl.toString()))
            Navigation.findNavController(view!!).navigate(R.id.action_addMemoryFragment_to_mapFragment3)
        })

        return binding.root
    }

    private fun selectImg(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,102)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 102 && resultCode == RESULT_OK){
            imageURI = data?.data!!
            binding.uploadImgIv.setImageURI(imageURI)
        }
    }

}