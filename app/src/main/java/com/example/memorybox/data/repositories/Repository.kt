package com.example.memorybox.data.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.models.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Repository (private val apiService: APIService){


    private lateinit var firebaseAuth: FirebaseAuth


    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    private var userMongoLiveData: MutableLiveData<User> = MutableLiveData()


    private var imgLinkLiveData: MutableLiveData<String> = MutableLiveData()

    private var imgIdLinkLiveData: MutableLiveData<String> = MutableLiveData()
    private var imgResumeLinkLiveData: MutableLiveData<String> = MutableLiveData()
    private var memoriesLiveData:MutableLiveData<List<Memory>> = MutableLiveData()
    private var competitionLiveData:MutableLiveData<List<Competition>> = MutableLiveData()

    private var teamsLiveData:MutableLiveData<List<Team>> = MutableLiveData()

    suspend fun getMemories(){
        val result = apiService.getMemories()
        if(result?.body()!= null){
            memoriesLiveData.postValue(result.body())
        }

    }

    suspend fun getCompetitions(){
        val result = apiService.getCompetitions()
        if(result?.body()!= null){
            Log.d("competition", result.body().toString())
            competitionLiveData.postValue(result.body())
        }

    }




    suspend fun addUser(user: User){
        Log.d("google sign in",user.toString())
        apiService.addUser(user)
    }

    suspend fun addTeam(team: Team){

        apiService.addTeam(team)
    }

    suspend fun addUserProfile(user: User){
        Log.d("google sign in",user.toString())
        apiService.addUserProfile(user)
    }

    fun getUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun uploadImg(imgUri: Uri){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imgUri)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    //addMemory(Memory(desc=desc, imgLink = it.toString()))
                    imgLinkLiveData.postValue(it.toString())
                }
            }
    }


    fun uploadResumeImg(imgUri: Uri){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imgUri)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    //addMemory(Memory(desc=desc, imgLink = it.toString()))
                    imgResumeLinkLiveData.postValue(it.toString())
                }
            }
    }

    fun uploadIdImg(imgUri: Uri){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imgUri)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    //addMemory(Memory(desc=desc, imgLink = it.toString()))
                    imgIdLinkLiveData.postValue(it.toString())
                }
            }
    }

    suspend fun addMemory(memory: Memory){

        apiService.addMemory(memory)

    }


    suspend fun addRequest(teamId: String, name: String){

        apiService.addRequest(teamId, name)

    }

    suspend fun getUserById(id: String){

        Log.d("google login", "from repo")
        val result = apiService.getUserById(id)
        if(result?.body()!= null){
            Log.d("google login", result.body().toString())
            userMongoLiveData.postValue(result.body())
        }

    }

    suspend fun getTeamsByComp(id: String){
        Log.d("teamsssss","sfdfdfdfffffff")
        val result = apiService.getTeamsByComp(id)
        if(result?.body()!= null){
            Log.d("google loginfromteam", result.body().toString())
            teamsLiveData.postValue(result.body())
        }

    }

    suspend fun getTeamsByUserId(googleId: String){
        Log.d("teamsssss","sfdfdfdfffffff")
        val result = apiService.getTeamsByUserId(googleId)
        if(result?.body()!= null){
            Log.d("google loginfromteam", result.body().toString())
            teamsLiveData.postValue(result.body())
        }

    }

    suspend fun testAPI(){

        apiService.testAPI()

    }



    fun googleLogin(account: GoogleSignInAccount?){


        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());

        }else{
            val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("google login", "google login success")
                    Log.d("google login", firebaseAuth.currentUser.toString())


                    userLiveData.postValue(firebaseAuth.currentUser)
                }
        }



    }

    fun logout() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        Log.d("google login", firebaseAuth.currentUser.toString())

    }

    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userLiveData
    }
    fun getImgLinkLiveData() : MutableLiveData<String>{
        return imgLinkLiveData
    }

    fun getImgIdLinkLiveData() : MutableLiveData<String>{
        return imgIdLinkLiveData
    }

    fun getImgResumeLinkLiveData() : MutableLiveData<String>{
        return imgResumeLinkLiveData
    }

    fun getMemoriesLiveData() : MutableLiveData<List<Memory>>{
        return memoriesLiveData
    }

    fun getCompetitionsLiveData() : MutableLiveData<List<Competition>>{
        return competitionLiveData
    }

    fun getTeamsLiveData() : MutableLiveData<List<Team>>{
        return teamsLiveData
    }
    fun getUserMongoLiveData() : MutableLiveData<User>{
        return userMongoLiveData
    }

}