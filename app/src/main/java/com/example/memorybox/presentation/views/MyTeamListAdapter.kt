package com.example.memorybox.presentation.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memorybox.R
import com.example.memorybox.data.models.Team


class MyTeamListAdapter: ListAdapter<Team, MyTeamListAdapter.MyTeamListViewHolder>(DiffUtil()) {


    //private lateinit var mListener:onItemClickListener

//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnitemClickListener(listener: onItemClickListener){
//        mListener = listener
//    }

    class MyTeamListViewHolder(view: View, ) : RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.my_comp_name)

        val reqs = view.findViewById<TextView>(R.id.my_comp_reqs)
        val compName = view.findViewById<TextView>(R.id.comp_date)

//        init{
//            view.setOnClickListener{
//                listener.onItemClick(position = adapterPosition)
//            }
//        }
        fun bind(team: Team){

            var allReqs = "Requests By: "

            for(reqs in team.requests){
                allReqs = allReqs + reqs + " , "
            }
            name.text = team.team
            reqs.text = allReqs
            compName.text = "Competition: " + team.competitionName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamListViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_competition_item, parent, false)
        return MyTeamListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyTeamListViewHolder, position: Int) {

        val team = getItem(position)
        holder.bind(team)


    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Team>(){
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            //change
            return oldItem.competitionDate == newItem.competitionDate
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {

            return oldItem == newItem
        }

    }
}