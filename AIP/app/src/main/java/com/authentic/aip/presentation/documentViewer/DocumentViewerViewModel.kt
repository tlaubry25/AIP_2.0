package com.authentic.aip.presentation.documentViewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.GetAttachmentInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentViewerViewModel @Inject constructor(
    private val attachmentInteractor : GetAttachmentInteractor
): ViewModel() {
    private val _attachmentData = MutableLiveData<AttachmentState>()
    val attachmentLiveData : LiveData<AttachmentState> = _attachmentData

    fun getAttachment(uid: String, cddeid: String, deli: Int?, doct: String,docName: String){
        attachmentInteractor(uid, cddeid, deli, doct, docName).onEach { result->
            when(result){
                is Resource.Error ->{ _attachmentData.value = AttachmentState(error = "testError") }
                is Resource.Loading ->{ _attachmentData.value = AttachmentState(isLoading = true)  }
                is Resource.Success ->{ _attachmentData.value = AttachmentState(attachmentData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}