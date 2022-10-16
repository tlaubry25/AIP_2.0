package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAttachmentInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, deli: Int?, doct: String,docName: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val attachment = repository.getAttachement(uid = uid, cddeid = cddeid, deli = deli, doct = doct, docName = docName).data
            emit(Resource.Success<String>(attachment))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<String>("Couldn't reach server. Check your internet connection"))
        }
    }
}