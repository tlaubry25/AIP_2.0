package com.authentic.aip.presentation.request

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.DenyRequestInteractor
import com.authentic.aip.domain.interactor.ExplainRequestInteractor
import com.authentic.aip.domain.interactor.RequestInteractor
import com.authentic.aip.domain.interactor.ValidateRequestInteractor
import com.authentic.aip.presentation.MessageManager
import com.authentic.aip.presentation.generic.GenericState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestInteractor : RequestInteractor
):ViewModel(){
    private val _requestDetailData = MutableLiveData<RequestState>()
    val requestDetailLiveData : LiveData<RequestState> = _requestDetailData

    fun request(context: Context,uid: String, cddeid: String, orderType: Char?){
        requestInteractor(uid, cddeid, orderType).onEach { result->
            when(result){
                is Resource.Error ->{
                    _requestDetailData.value = RequestState(error = result.message?:"ErrorWebservice")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{
                    _requestDetailData.value = RequestState(isLoading = true)  }
                is Resource.Success ->{
                    _requestDetailData.value = RequestState(requestData = result.data) }
            }
        }.launchIn(viewModelScope)
    }




}