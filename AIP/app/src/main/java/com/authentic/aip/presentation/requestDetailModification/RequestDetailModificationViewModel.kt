package com.authentic.aip.presentation.requestDetailModification

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.RequestDetailModificationInteractor
import com.authentic.aip.presentation.MessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestDetailModificationViewModel @Inject constructor(
    private val requestDetailModificationInteractor : RequestDetailModificationInteractor
): ViewModel(){
    private val _requestDetailModificationData = MutableLiveData<RequestDetailModificationState>()
    val requestDetailModificationLiveData : LiveData<RequestDetailModificationState> = _requestDetailModificationData

    fun getRequestChanges(context: Context, uid : String, cddeid : String, deli:Int){
        requestDetailModificationInteractor(uid, cddeid, deli).onEach { result->
            when(result){
                is Resource.Error ->{ _requestDetailModificationData.value = RequestDetailModificationState(error = result.message?:"ErrorWebservice")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _requestDetailModificationData.value = RequestDetailModificationState(isLoading = true)  }
                is Resource.Success ->{ _requestDetailModificationData.value = RequestDetailModificationState(requestModificationData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}