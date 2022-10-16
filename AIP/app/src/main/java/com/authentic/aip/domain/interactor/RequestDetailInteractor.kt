package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.requestDetail.toRequestDetail
import com.authentic.aip.domain.model.RequestDetail
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestDetailInteractor@Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, orderType: Char, originalOrder: Int?, numPg: Int): Flow<Resource<RequestDetail>> = flow {
        try {
            emit(Resource.Loading<RequestDetail>())
            val requestDetail = repository.listRequestLines(uid = uid, cddeid = cddeid, orderType = orderType, originalOrder = originalOrder, numPg = numPg).data.toRequestDetail()
            emit(Resource.Success<RequestDetail>(requestDetail))
        } catch (e: HttpException) {
            emit(Resource.Error<RequestDetail>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<RequestDetail>("Couldn't reach server. Check your internet connection"))
        }
    }
}