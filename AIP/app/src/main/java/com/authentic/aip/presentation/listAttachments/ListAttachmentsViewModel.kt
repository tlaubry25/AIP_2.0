package com.authentic.aip.presentation.listAttachments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.ListAttachmentsInteractor
import com.authentic.aip.domain.interactor.ListRequestInteractor
import com.authentic.aip.presentation.listRequest.ListRequestState
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

    fun listAttachments(uid: String, cddeid: String, deli: Int, numPg: Int){
        listAttachmentsInteractor(uid, cddeid, deli, numPg).onEach { result->
            when(result){
                is Resource.Error ->{ _listAttachmentsData.value = ListAttachmentsState(error = "testError") }
                is Resource.Loading ->{ _listAttachmentsData.value = ListAttachmentsState(isLoading = true)  }
                is Resource.Success ->{ _listAttachmentsData.value = ListAttachmentsState(listAttachments = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}