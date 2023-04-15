
package com.example.memorybox.presentation.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.ActivityLoginBinding
import com.example.memorybox.presentation.viewmodel.LoginViewModel
import com.example.memorybox.presentation.viewmodel.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1045593328418-34vj4qjgjoisougs5st5f28jqstd50h6.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.imageView.setOnClickListener {
            Log.d("google login", "google login initiated")
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        }

        loginViewModel.getUserLiveData().observe(this,{
            Log.d("google sign in", it.uid.toString()!!)
            loginViewModel.addUser(
                User(name = it.displayName!!
                    ,profilePicLink = it.photoUrl.toString()!!
                    , email = it.email!!, googleId = it.uid, additionalLinks = "", branch = "", competingExp = "", idLink = "", resumeLink = "", rollNo = "", societyExp = "")
            )

            // Navigation.findNavController(view!!).navigate(R.id.action_loginFragment3_to_mapFragment3)

            Handler().postDelayed({
                //doSomethingHere()


                loginViewModel.getUserById(it.uid.toString())
            }, 1000)
//            val myIntent = Intent(this, AddInfoActivity::class.java)
//            startActivity(myIntent)
        })


        loginViewModel.getUserMongoLiveData().observe(this,{
            Log.d("google login", it.toString())
            Log.d("google login", "hihiihihi")

//            loginViewModel.addUser(
//                User(name = it.displayName!!
//                    ,profilePicLink = it.photoUrl.toString()!!
//                    , email = it.email!!)
//            )

            // Navigation.findNavController(view!!).navigate(R.id.action_loginFragment3_to_mapFragment3)

            if(it.idLink != ""){
                Log.d("google login", "main")
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
            }else{
                Log.d("google login", "addinfo")
                val myIntent = Intent(this, AddInfoActivity::class.java)
                startActivity(myIntent)
            }

        })






    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = accountTask.getResult(ApiException::class.java)
                Log.d("google login", account.toString())
                loginViewModel.googleLogin(account)

            }catch(e:Exception){

                Log.d("google login error", e.toString())
            }
        }
    }




}