package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.LoginDto
import com.authentic.aip.data.remote.dto.LoginResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AipApi {

    @POST("login")
    suspend fun loginRequest(@Query("login")login : String, @Query("pwd")pwd : String, @Query("lng")lng:String): LoginResponseDto

    @GET("/getNbRequest")
    suspend fun getNbRequest(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean?): GetNbRequestResponseDto
}