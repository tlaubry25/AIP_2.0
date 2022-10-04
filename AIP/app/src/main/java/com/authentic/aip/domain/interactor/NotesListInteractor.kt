package com.authentic.aip.domain.interactor

import com.authentic.aip.common.Resource
import com.authentic.aip.data.remote.dto.notesList.toNotesList
import com.authentic.aip.domain.model.NotesList
import com.authentic.aip.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotesListInteractor @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(uid: String, cddeid: String, deli: Int, numPg: Int): Flow<Resource<NotesList>> = flow {
        try {
            emit(Resource.Loading<NotesList>())
            val notesList = repository.listNotes(uid = uid, cddeid = cddeid, deli = deli, numPg = numPg).data.toNotesList()
            emit(Resource.Success<NotesList>(notesList))
        } catch (e: HttpException) {
            emit(Resource.Error<NotesList>(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error<NotesList>("Couldn't reach server. Check your internet connection"))
        }
    }
}