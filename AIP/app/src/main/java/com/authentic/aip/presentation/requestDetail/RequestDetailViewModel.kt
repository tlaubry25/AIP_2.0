package com.authentic.aip.presentation.requestDetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.RequestDetailInteractor
import com.authentic.aip.presentation.MessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestDetailViewModel @Inject constructor(
    private val requestDetailInteractor : RequestDetailInteractor
): ViewModel(){
    private val _requestDetailData = MutableLiveData<RequestDetailState>()
    val requestDetailLiveData : LiveData<RequestDetailState> = _requestDetailData

    fun requestDetail(context: Context,uid: String, cddeid: String, orderType: Char, originalOrder: Int?, numPg: Int){
        requestDetailInteractor(uid, cddeid, orderType, originalOrder, numPg).onEach { result->
            when(result){
                is Resource.Error ->{
                    _requestDetailData.value = RequestDetailState(error = result.message?:"ErrorWebservice")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{
                    _requestDetailData.value = RequestDetailState(isLoading = true)  }
                is Resource.Success ->{
                    _requestDetailData.value = RequestDetailState(requestDetailData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}