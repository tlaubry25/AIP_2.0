package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MenuInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid:String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val nbRequest = repository.getNbRequest(uid = uid, orderType = '0', histo = false).data
            emit(Resource.Success<Int>(nbRequest))
        } catch (e: HttpException) {
            emit(Resource.Error<Int>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<Int>("Couldn't reach server. Check your internet connection"))
        }
    }
}