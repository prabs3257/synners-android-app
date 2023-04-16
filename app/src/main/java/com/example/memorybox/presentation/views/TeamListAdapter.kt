package com.example.memorybox.presentation.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memorybox.R
import com.example.memorybox.data.models.Team


class TeamListAdapter: ListAdapter<Team, TeamListAdapter.TeamListViewHolder>(DiffUtil()) {


    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnitemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class TeamListViewHolder(view: View, listener: TeamListAdapter.onItemClickListener) : RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.team_name)
        val skills = view.findViewById<TextView>(R.id.team_skills)

        init{
            view.setOnClickListener{
                listener.onItemClick(position = adapterPosition)
            }
        }
        fun bind(team: Team){
            name.text = team.team
            skills.text = team.skills
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return TeamListViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {

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