package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.login.toLogin
import com.authentic.aip.domain.model.Login
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(login: String, password: String, language:String): Flow<Resource<Login>> = flow {
        try {
            emit(Resource.Loading<Login>())
            val login = repository.getLogin(login = login, password = password,language).data.toLogin()
            emit(Resource.Success<Login>(login))
        } catch (e: HttpException) {
            emit(Resource.Error<Login>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<Login>("Couldn't reach server. Check your internet connection"))
        }
    }
}