package com.authentic.aip.presentation.documentViewer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.GetAttachmentInteractor
import com.authentic.aip.presentation.MessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentViewerViewModel @Inject constructor(
    private val attachmentInteractor : GetAttachmentInteractor
): ViewModel() {
    private val _attachmentData = MutableLiveData<GetAttachmentState>()
    val attachmentLiveData : LiveData<GetAttachmentState> = _attachmentData

    fun getAttachment(context: Context, uid: String, cddeid: String, deli: Int?, doct: String, docName: String){
        attachmentInteractor(uid, cddeid, deli, doct, docName).onEach { result->
            when(result){
                is Resource.Error ->{ _attachmentData.value = GetAttachmentState(error = "testError")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _attachmentData.value = GetAttachmentState(isLoading = true)  }
                is Resource.Success ->{ _attachmentData.value = GetAttachmentState(attachmentData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}