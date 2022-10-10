package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.listRequest.toListRequest
import com.authentic.aip.domain.model.ListRequest
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListRequestInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid : String, orderType : Char, histo:Int, numPg:Int): Flow<Resource<ListRequest>> = flow {
        try {
            emit(Resource.Loading<ListRequest>())
            val listRequest = repository.listRequests(uid = uid, orderType = orderType, histo = histo, numPg = numPg).data.toListRequest()
            emit(Resource.Success<ListRequest>(listRequest))
        } catch (e: HttpException) {
            emit(Resource.Error<ListRequest>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<ListRequest>("Couldn't reach server. Check your internet connection"))
        }
    }
}