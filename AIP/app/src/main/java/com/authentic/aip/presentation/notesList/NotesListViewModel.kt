package com.authentic.aip.presentation.notesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.NotesListInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesListInteractor : NotesListInteractor
): ViewModel(){
    private val _notesListData = MutableLiveData<NotesListState>()
    val notesListLiveData : LiveData<NotesListState> = _notesListData

    fun notesList(uid: String, cddeid: String, deli: Int, numPg: Int){
        notesListInteractor(uid, cddeid, deli, numPg).onEach { result->
            when(result){
                is Resource.Error ->{
                    _notesListData.value = NotesListState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _notesListData.value = NotesListState(isLoading = true)  }
                is Resource.Success ->{
                    _notesListData.value = NotesListState(notesList = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}