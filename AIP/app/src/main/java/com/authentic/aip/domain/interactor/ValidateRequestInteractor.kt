package com.authentic.aip.domain.interactor

import android.util.Log
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ValidateRequestInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): Flow<Resource<Any>> = flow {
        try {

            emit(Resource.Loading<Any>())
            val any = repository.validateRequest(uid =  uid, cddeid = cddeid, deli = deli, orderType = orderType, comment = comment).data
            Log.d("AIP", ""+any)
            emit(Resource.Success<Any>(any))
        } catch (e: HttpException) {
            emit(Resource.Error<Any>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<Any>("Couldn't reach server. Check your internet connection"))
        }
    }
}