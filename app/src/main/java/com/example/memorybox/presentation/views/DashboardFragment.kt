package com.example.memorybox.presentation.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.FragmentDashboardBinding
import com.example.memorybox.databinding.FragmentLoginBinding
import com.example.memorybox.presentation.viewmodel.DashboardFragmentViewModel
import com.example.memorybox.presentation.viewmodel.DashboardFragmentViewModelFactory
import com.example.memorybox.presentation.viewmodel.LoginViewModel
import com.example.memorybox.presentation.viewmodel.LoginViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {


    private lateinit var binding: FragmentDashboardBinding

    private lateinit var dashboardFragmentViewModel: DashboardFragmentViewModel

    private var teams:List<Team>? =null
    val adapter = MyTeamListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)

        dashboardFragmentViewModel = ViewModelProvider(this, DashboardFragmentViewModelFactory(repository)).get(
            DashboardFragmentViewModel::class.java)



        dashboardFragmentViewModel.getTeamByUserId(dashboardFragmentViewModel.getUser()!!.uid)

        dashboardFragmentViewModel.getTeamLiveData().observe(this.requireActivity(),{
            teams=it
            Log.d("skk", teams.toString())
            adapter.submitList(teams)

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false)

        binding.reloadBtn.setOnClickListener {
            dashboardFragmentViewModel.getTeamByUserId(dashboardFragmentViewModel.getUser()!!.uid)

            dashboardFragmentViewModel.getTeamLiveData().observe(this.requireActivity(),{
                teams=it
                Log.d("skk", teams.toString())
                adapter.submitList(teams)

            })
        }


        val recyclerView = binding.myCompetitionRecyclerView

//


        recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        return binding.root
    }


}