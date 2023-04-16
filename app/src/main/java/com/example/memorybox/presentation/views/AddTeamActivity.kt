package com.example.memorybox.presentation.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.ActivityAddTeamBinding
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModel
import com.example.memorybox.presentation.viewmodel.AddMemoryViewModelFactory
import com.example.memorybox.presentation.viewmodel.AddTeamViewModel
import com.example.memorybox.presentation.viewmodel.AddTeamViewModelFactory

class AddTeamActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddTeamBinding

    private lateinit var addTeamViewModel: AddTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)
        addTeamViewModel = ViewModelProvider(this, AddTeamViewModelFactory(repository)).get(
            AddTeamViewModel::class.java)

        binding.submitBtn.setOnClickListener {
            addTeamViewModel.addTeam(Team(competitionName = binding.compNameEt.getText().toString()
                , competitionWebsite = binding.compWebEt.getText().toString()
                , competitionDate = binding.compDateEt.getText().toString()
                , membersNum = binding.noMembersEt.getText().toString()
                , skills = binding.skillsEt.getText().toString()
                , projectIdea = binding.projectEt.getText().toString()
                , objective = ""
                , googleId = addTeamViewModel.getUser()?.uid
                , membersName = binding.nameMembersEt.getText().toString()
                , team = binding.teamNameEt.getText().toString()
                , _id = ""
                , requests = emptyList()
            ))

            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }


    }


}