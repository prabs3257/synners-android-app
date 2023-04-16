package com.example.memorybox.presentation.views

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.memorybox.R
import com.example.memorybox.databinding.ActivityTeamProfileBinding


class TeamProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        supportActionBar?.hide()
        binding = ActivityTeamProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent
        val teamName = intent.getStringExtra("teamName")
        val teamMembersName = intent.getStringExtra("teamMembersName")
        val teamSkills = intent.getStringExtra("teamSkills")
        val teamIdea = intent.getStringExtra("teamIdea")
        val teamId = intent.getStringExtra("teamId")

        binding.teamName.text = teamName

        val memberNames = teamMembersName.toString().split(",").toTypedArray()
        val memberSkills = teamSkills.toString().split(",").toTypedArray()

        for (name in memberNames){
            val textView = TextView(this)
            textView.setText(name.toString())
            textView.setPadding(200, 20, 200, 20)

            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(260, 20, 10, 10)
            textView.setLayoutParams(params)

            textView.setBackgroundResource(R.drawable.round_back_edit_text_grey)
            binding.membersLl.addView(textView)

        }


        for (skill in memberSkills){
            val textView = TextView(this)
            textView.setText(skill.toString())
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView.setPadding(20, 20, 20, 20)
            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(30, 20, 10, 10)
            textView.setLayoutParams(params)


            textView.setBackgroundResource(R.drawable.round_back_edit_text_grey)
            binding.skillsLl.addView(textView)

        }

        binding.applyBtn.setOnClickListener {
            val bottomSheetFragment = ApplyBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString("teamId", teamId.toString())
            bottomSheetFragment.arguments = bundle


            bottomSheetFragment.show(supportFragmentManager,"sheet")
        }




    }
}