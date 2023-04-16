package com.example.memorybox.presentation.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorybox.databinding.FragmentLoginBinding
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.presentation.viewmodel.LoginViewModel
import com.example.memorybox.presentation.viewmodel.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException



class LoginFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private var competitions:List<Competition>? =null
    val adapter = CompetitionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)

        loginViewModel = ViewModelProvider(this,LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        loginViewModel.getCompetitions()

        loginViewModel.getCompetitionLiveData().observe(this.requireActivity(),{
            competitions=it
            Log.d("s", competitions.toString())
            adapter.submitList(competitions)

        })



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)


        val recyclerView = binding.competitionRecyclerView

//


        recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        adapter.setOnitemClickListener(object :CompetitionAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val myIntent = Intent(requireContext(), TeamListActivity::class.java)
                myIntent.putExtra("compId", competitions?.get(position)?._id.toString())
                myIntent.putExtra("compName", competitions?.get(position)?.name.toString())
                startActivity(myIntent)
            }

        })
//        binding.fragmentGoogleLoginBtn.setOnClickListener {
//            Log.d("google login", "google login initiated")
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, 100)
//        }
//
//        binding.fragmentLogoutBtn.setOnClickListener {
//            loginViewModel.logout()
//        }
//
//        loginViewModel.getUserLiveData().observe(this.requireActivity(),{
//            Log.d("google sign in", it.photoUrl.toString()!!)
////            loginViewModel.addUser(User(name = it.displayName!!
////                ,profilePicLink = it.photoUrl.toString()!!
////                , email = it.email!!))
//
//           // Navigation.findNavController(view!!).navigate(R.id.action_loginFragment3_to_mapFragment3)
//        })


        return binding.root
    }


}