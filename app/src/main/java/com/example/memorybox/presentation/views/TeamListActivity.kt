package com.example.memorybox.presentation.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.ActivityTeamListBinding
import com.example.memorybox.presentation.viewmodel.LoginViewModel
import com.example.memorybox.presentation.viewmodel.LoginViewModelFactory
import com.example.memorybox.presentation.viewmodel.TeamListViewModel
import com.example.memorybox.presentation.viewmodel.TeamListViewModelFactory


class TeamListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTeamListBinding

    private lateinit var teamListViewModel: TeamListViewModel

    private var teams:List<Team>? =null
    val adapter = TeamListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)

        teamListViewModel = ViewModelProvider(this, TeamListViewModelFactory(repository)).get(
            TeamListViewModel::class.java)



        val intent = intent
        val str = intent.getStringExtra("compId")
        val strName = intent.getStringExtra("compName")

        Log.d("hghgghg", str.toString())
        binding.compName.text = strName


        teamListViewModel.getTeamsByComp(str.toString())

        teamListViewModel.getTeamsLiveData().observe(this,{
            teams=it
            Log.d("dsdjjjjjj", teams.toString())

            adapter.submitList(teams)

        })


        val recyclerView = binding.teamListRecyclerView


        adapter.setOnitemClickListener(object :TeamListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.d("dsdjjjjjj", "dvkkkkkhkhkkhkhkh")
                val myIntent = Intent(this@TeamListActivity, TeamProfileActivity::class.java)
                myIntent.putExtra("teamName", teams?.get(position)?.team.toString())
                myIntent.putExtra("teamMembersName", teams?.get(position)?.membersName.toString())
                myIntent.putExtra("teamSkills", teams?.get(position)?.skills.toString())
                myIntent.putExtra("teamIdea", teams?.get(position)?.projectIdea.toString())
                myIntent.putExtra("teamId", teams?.get(position)?._id.toString())
                startActivity(myIntent)
            }

        })
//


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter






    }


}