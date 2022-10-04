package com.authentic.aip.domain.repository

import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import retrofit2.http.Query

interface AppRepository {

    suspend fun getLogin(login:String, password : String): LoginResponseDto
    suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto
    suspend fun listRequests(uid : String, orderType : Char, histo:Boolean, numPg:Int): ListRequestResponseDto
    suspend fun viewRequestHead(uid : String, cddeid : String, orderType:Char?): RequestDetailResponseDto
}