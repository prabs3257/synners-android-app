package com.example.memorybox.presentation.views

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memorybox.R
import com.example.memorybox.data.models.Competition


class CompetitionAdapter: ListAdapter<Competition, CompetitionAdapter.CompetitionViewHolder>(DiffUtil()) {

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnitemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    class CompetitionViewHolder(view: View, listener:onItemClickListener) : RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.comp_name)
        val date = view.findViewById<TextView>(R.id.comp_date)

        init{
            view.setOnClickListener{
                listener.onItemClick(position = adapterPosition)
            }
        }

        fun bind(competition: Competition){
            name.text = competition.name
            date.text = competition.date
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.competition_item, parent, false)
        return CompetitionViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {

        val competition = getItem(position)
        holder.bind(competition)

    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Competition>(){
        override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {

            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {

            return oldItem == newItem
        }

    }
}