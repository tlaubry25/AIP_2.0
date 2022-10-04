package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AipApi {

    @POST("login")
    suspend fun loginRequest(@Query("login")login : String, @Query("pwd")pwd : String, @Query("lng")lng:String): LoginResponseDto

    @GET("getNbRequests")
    suspend fun getNbRequest(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean?): GetNbRequestResponseDto

    @GET("listRequests")
    suspend fun listRequests(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean, @Query("numPg")numPg:Int): ListRequestResponseDto

    @GET("viewRequestHead")
    suspend fun viewRequestHead(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char?): RequestDetailResponseDto
}