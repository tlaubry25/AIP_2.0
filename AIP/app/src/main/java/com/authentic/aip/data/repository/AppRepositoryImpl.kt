package com.authentic.aip.data.repository

import com.authentic.aip.data.remote.api.AipApi
import com.authentic.aip.data.remote.dto.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.LoginResponseDto
import com.authentic.aip.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private var appApi : AipApi):AppRepository{

    override suspend fun getLogin(login:String, password : String): LoginResponseDto {
        return appApi.loginRequest(login, password, "fr")
    }

    override suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto {
        return appApi.getNbRequest(uid, orderType, histo)
    }

}