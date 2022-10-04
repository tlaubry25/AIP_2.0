package com.authentic.aip.presentation.requestDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.MenuInteractor
import com.authentic.aip.domain.interactor.RequestDetailInteractor
import com.authentic.aip.presentation.menu.MenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestDetailViewModel @Inject constructor(
    private val requestDetailInteractor : RequestDetailInteractor
):ViewModel(){
    private val _requestDetailData = MutableLiveData<RequestDetailState>()
    val requestDetailLiveData : LiveData<RequestDetailState> = _requestDetailData

    fun requestDetail(uid: String, cddeid: String, orderType: Char?){
        requestDetailInteractor(uid, cddeid, orderType).onEach { result->
            when(result){
                is Resource.Error ->{
                    _requestDetailData.value = RequestDetailState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _requestDetailData.value = RequestDetailState(isLoading = true)  }
                is Resource.Success ->{
                    _requestDetailData.value = RequestDetailState(requestData = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}