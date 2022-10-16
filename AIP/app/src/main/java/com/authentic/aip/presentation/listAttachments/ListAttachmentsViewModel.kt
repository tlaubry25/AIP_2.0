package com.authentic.aip.presentation.listAttachments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.GetAttachmentInteractor
import com.authentic.aip.domain.interactor.ListAttachmentsInteractor
import com.authentic.aip.presentation.documentViewer.GetAttachmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListAttachmentsViewModel @Inject constructor(
private val listAttachmentsInteractor : ListAttachmentsInteractor,
private val attachmentContentInteractor : GetAttachmentInteractor
): ViewModel() {
    private val _listAttachmentsData = MutableLiveData<ListAttachmentsState>()
    val listAttachmentsLiveData : LiveData<ListAttachmentsState> = _listAttachmentsData

    private val _getAttachmentsData = MutableLiveData<GetAttachmentState>()
    val getAttachmentsLiveData : LiveData<GetAttachmentState> = _getAttachmentsData

    fun listAttachments(uid: String, cddeid: String, deli: Int, numPg: Int){
        listAttachmentsInteractor(uid, cddeid, deli, numPg).onEach { result->
            when(result){
                is Resource.Error ->{ _listAttachmentsData.value = ListAttachmentsState(error = "testError") }
                is Resource.Loading ->{ _listAttachmentsData.value = ListAttachmentsState(isLoading = true)  }
                is Resource.Success ->{ _listAttachmentsData.value = ListAttachmentsState(listAttachments = result.data) }
            }
        }.launchIn(viewModelScope)
    }

    fun getAttachments(uid: String, cddeid: String, deli: Int?=0, doct: String,docName: String){
        attachmentContentInteractor(uid, cddeid, deli, doct, docName).onEach { result->
            when(result){
                is Resource.Error ->{ _getAttachmentsData.value = GetAttachmentState(error = "testError") }
                is Resource.Loading ->{ _getAttachmentsData.value = GetAttachmentState(isLoading = true)  }
                is Resource.Success ->{ _getAttachmentsData.value = GetAttachmentState(attachmentData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}