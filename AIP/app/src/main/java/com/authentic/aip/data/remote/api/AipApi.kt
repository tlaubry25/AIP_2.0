package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.LoginDto
import com.authentic.aip.data.remote.dto.LoginResponseDto
import retrofit2.http.POST
import retrofit2.http.Query

interface AipApi {

    @POST("login")
    fun loginRequest(@Query("login")login : String, @Query("pwd")pwd : String, @Query("lng")lng:String): LoginResponseDto
}