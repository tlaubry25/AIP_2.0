package com.authentic.aip.presentation.listAttachments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.GetAttachmentInteractor
import com.authentic.aip.domain.interactor.ListAttachmentsInteractor
import com.authentic.aip.presentation.MessageManager
import com.authentic.aip.presentation.documentViewer.GetAttachmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListAttachmentsViewModel @Inject constructor(
private val listAttachmentsInteractor : ListAttachmentsInteractor
): ViewModel() {
    private val _listAttachmentsData = MutableLiveData<ListAttachmentsState>()
    val listAttachmentsLiveData : LiveData<ListAttachmentsState> = _listAttachmentsData

    fun listAttachments(context: Context, uid: String, cddeid: String, deli: Int, numPg: Int){
        listAttachmentsInteractor(uid, cddeid, deli, numPg).onEach { result->
            when(result){
                is Resource.Error ->{ _listAttachmentsData.value = ListAttachmentsState(error = "testError")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _listAttachmentsData.value = ListAttachmentsState(isLoading = true)  }
                is Resource.Success ->{ _listAttachmentsData.value = ListAttachmentsState(listAttachments = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}