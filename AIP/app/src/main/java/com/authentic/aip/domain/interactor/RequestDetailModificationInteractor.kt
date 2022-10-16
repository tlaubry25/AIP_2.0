package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.requestDetailModification.toRequestDetailModification
import com.authentic.aip.domain.model.RequestDetailModification
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestDetailModificationInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, deli: Int): Flow<Resource<RequestDetailModification>> = flow {
        try {
            emit(Resource.Loading<RequestDetailModification>())
            val requestDetailModification = repository.getRequestLineChanges(uid = uid, cddeid = cddeid, deli = deli).data?.toRequestDetailModification()
            emit(Resource.Success<RequestDetailModification>(requestDetailModification))
        } catch (e: HttpException) {
            emit(Resource.Error<RequestDetailModification>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<RequestDetailModification>("Couldn't reach server. Check your internet connection"))
        }
    }
}