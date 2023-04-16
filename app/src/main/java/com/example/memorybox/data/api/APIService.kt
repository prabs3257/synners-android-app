package com.example.memorybox.data.api

import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.models.User

import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("user")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addUser(@Body user: User)

    @POST("user/createTeam")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addTeam(@Body team: Team)

    @POST("user/profile")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addUserProfile(@Body user: User)

    @POST("addMemory")
    @Headers("Accept:application/json", "Content-Type:application/json")
    suspend fun addMemory(@Body memory: Memory)

    @GET("getMemories")
    suspend fun getMemories(): Response<List<Memory>>

    @GET("competitions")
    suspend fun getCompetitions(): Response<List<Competition>>

    @GET("user/getUserById")
    suspend fun getUserById(@Query("googleId") id:String): Response<User>

    @GET("competitions/teams")
    suspend fun getTeamsByComp(@Query("_id") id:String): Response<List<Team>>

    @GET("team/getTeams")
    suspend fun getTeamsByUserId(@Query("googleId") id:String): Response<List<Team>>

    @GET("test")
    suspend fun testAPI()

    @GET("team/addRequest")
    suspend fun addRequest(@Query("teamId") teamId:String, @Query("name") name:String)




}