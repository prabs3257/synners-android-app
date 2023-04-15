package com.example.memorybox.presentation.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.ActivityAddInfoBinding
import com.example.memorybox.presentation.viewmodel.AddInfoViewModel
import com.example.memorybox.presentation.viewmodel.AddInfoViewModelFactory
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModel
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModelFactory
import com.google.firebase.auth.FirebaseUser

class AddInfoActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddInfoBinding

    private lateinit var addInfoViewModel: AddInfoViewModel
    private lateinit var imageIdURI: Uri
    private lateinit var imageResumeURI: Uri
    private var imgIdLink: String = ""
    private var imgResumeLink: String = ""




    private fun selectImgId(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,102)

    }

    private fun selectImgResume(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,103)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 103 && resultCode == RESULT_OK){
            imageResumeURI = data?.data!!
            binding.uploadResumeBtn.setImageURI(imageResumeURI)
        }

        if(requestCode == 102 && resultCode == RESULT_OK){
            imageIdURI = data?.data!!
            binding.uploadIdBtn.setImageURI(imageIdURI)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)
        addInfoViewModel = ViewModelProvider(this, AddInfoViewModelFactory(repository)).get(
            AddInfoViewModel::class.java)

        binding.submitBtn.setOnClickListener {
//
//            Log.d("dsdddd", addInfoViewModel.getUser()?.uid.toString())
            addInfoViewModel.uploadImgId(imageIdURI)


            Log.d("dsdddd", imgIdLink)
        }


        binding.uploadIdBtn.setOnClickListener {
            selectImgId()

        }
        fun uploadData(){

            if(imgIdLink != "" && imgResumeLink != ""){
                Log.d("dsdddd Id", imgIdLink)
                Log.d("dsdddd resume", imgResumeLink)

                addInfoViewModel.addUserProfile(
                User(name = addInfoViewModel.getUser()?.displayName!!
                    ,profilePicLink = addInfoViewModel.getUser()?.photoUrl.toString()!!
                    , email = addInfoViewModel.getUser()?.email!!
                    , googleId = addInfoViewModel.getUser()?.uid
                    , idLink = imgIdLink
                    , resumeLink = imgResumeLink
                    , branch = ""
                    , rollNo = ""
                    , competingExp = binding.compExpEt.getText().toString()
                    , societyExp = binding.socExpEt.getText().toString()
                    , additionalLinks = binding.linksEt.getText().toString()
                    )
            )

                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
            }

        }

        addInfoViewModel.getImgIdLinkLiveData().observe(this,{

            imgIdLink = it
            //Log.d("dsdddd2", imgIdLink)
            addInfoViewModel.uploadResumeImg(imageResumeURI)
            uploadData()

        })


        binding.uploadResumeBtn.setOnClickListener {
            selectImgResume()

        }



        addInfoViewModel.getImgResumeLinkLiveData().observe(this,{

            imgResumeLink = it

            uploadData()
        })
    }


}