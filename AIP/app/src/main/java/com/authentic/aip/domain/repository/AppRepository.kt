package com.authentic.aip.domain.repository

import com.authentic.aip.data.remote.dto.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.LoginResponseDto

interface AppRepository {

    suspend fun getLogin(login:String, password : String):LoginResponseDto
    suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto
}