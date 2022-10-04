package com.authentic.aip.data.repository

import com.authentic.aip.data.remote.api.AipApi
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import com.authentic.aip.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private var appApi : AipApi):AppRepository{

    override suspend fun getLogin(login:String, password : String): LoginResponseDto {
        return appApi.loginRequest(login, password, "fr")
    }

    override suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto {
        return appApi.getNbRequest(uid, orderType, histo)
    }

    override suspend fun listRequests(uid: String, orderType: Char, histo: Boolean, numPg: Int): ListRequestResponseDto {
        return appApi.listRequests(uid, orderType, histo, numPg)
    }

    override suspend fun viewRequestHead(uid: String, cddeid: String, orderType: Char?): RequestDetailResponseDto {
        return appApi.viewRequestHead(uid, cddeid, orderType)
    }

}