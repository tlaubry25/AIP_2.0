package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestTextInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, deli: Int): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val nbRequest = repository.getText(uid = uid, cddeid = cddeid, deli = deli).data
            emit(Resource.Success<String>(nbRequest))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<String>("Couldn't reach server. Check your internet connection"))
        }
    }
}