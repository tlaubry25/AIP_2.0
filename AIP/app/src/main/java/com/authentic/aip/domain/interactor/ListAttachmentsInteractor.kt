package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.listAttachments.toListAttachments
import com.authentic.aip.domain.model.ListAttachments
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListAttachmentsInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, deli: Int, numPg: Int): Flow<Resource<ListAttachments>> = flow {
        try {
            emit(Resource.Loading<ListAttachments>())
            val listAttachments = repository.listAttachements(uid = uid, cddeid = cddeid, deli = deli, numPg = numPg).data.toListAttachments()
            emit(Resource.Success<ListAttachments>(listAttachments))
        } catch (e: HttpException) {
            emit(Resource.Error<ListAttachments>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<ListAttachments>("Couldn't reach server. Check your internet connection"))
        }
    }
}