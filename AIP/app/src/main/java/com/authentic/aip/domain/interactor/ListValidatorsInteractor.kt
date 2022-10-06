package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.validatorList.toValidatorList
import com.authentic.aip.domain.model.ValidatorList
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListValidatorsInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String?, numPg: Int): Flow<Resource<ValidatorList>> = flow {
        try {
            emit(Resource.Loading<ValidatorList>())
            val listRequest = repository.listValidators(uid = uid, cddeid = cddeid, numPg = numPg).data.toValidatorList()
            emit(Resource.Success<ValidatorList>(listRequest))
        } catch (e: HttpException) {
            emit(Resource.Error<ValidatorList>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<ValidatorList>("Couldn't reach server. Check your internet connection"))
        }
    }
}