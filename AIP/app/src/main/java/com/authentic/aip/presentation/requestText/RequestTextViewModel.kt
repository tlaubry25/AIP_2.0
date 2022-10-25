package com.authentic.aip.presentation.requestText

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.RequestTextInteractor
import com.authentic.aip.presentation.MessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestTextViewModel @Inject constructor(
    private val requestTextInteractor : RequestTextInteractor
): ViewModel(){
    private val _requestTextData = MutableLiveData<RequestTextState>()
    val requestTextLiveData : LiveData<RequestTextState> = _requestTextData

    fun getNbRequest(context: Context, uid : String, cddeid : String, deli:Int){
        requestTextInteractor(uid, cddeid, deli).onEach { result->
            when(result){
                is Resource.Error ->{ _requestTextData.value = RequestTextState(error = result.message?:"ErrorWebservice")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _requestTextData.value = RequestTextState(isLoading = true)  }
                is Resource.Success ->{ _requestTextData.value = RequestTextState(requestText = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}