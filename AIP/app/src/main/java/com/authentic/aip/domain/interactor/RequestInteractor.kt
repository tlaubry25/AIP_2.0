package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.request.toRequest
import com.authentic.aip.domain.model.Request
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestInteractor@Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, orderType: Char?): Flow<Resource<Request>> = flow {
        try {
            emit(Resource.Loading<Request>())
            val request = repository.viewRequestHead(uid = uid, cddeid = cddeid, orderType = orderType).data.toRequest()
            emit(Resource.Success<Request>(request))
        } catch (e: HttpException) {
            emit(Resource.Error<Request>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<Request>("Couldn't reach server. Check your internet connection"))
        }
    }
}