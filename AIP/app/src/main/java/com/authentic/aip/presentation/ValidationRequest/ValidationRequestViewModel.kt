package com.authentic.aip.presentation.ValidationRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.DenyRequestInteractor
import com.authentic.aip.domain.interactor.ExplainRequestInteractor
import com.authentic.aip.domain.interactor.ListRequestInteractor
import com.authentic.aip.domain.interactor.ValidateRequestInteractor
import com.authentic.aip.presentation.generic.GenericState
import com.authentic.aip.presentation.listRequest.ListRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ValidationRequestViewModel @Inject constructor(
    private val validateRequestInteractor : ValidateRequestInteractor,
    private val denyRequestInteractor : DenyRequestInteractor,
    private val explainRequestInteractor : ExplainRequestInteractor
): ViewModel() {

    private val _validateRequestData = MutableLiveData<GenericState>()
    val validateRequestLiveData : LiveData<GenericState> = _validateRequestData

    private val _denyRequestData = MutableLiveData<GenericState>()
    val denyRequestLiveData : LiveData<GenericState> = _denyRequestData

    private val _explainRequestData = MutableLiveData<GenericState>()
    val explainDataLiveData : LiveData<GenericState> = _explainRequestData

    fun validateRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String){
        validateRequestInteractor(uid, cddeid, deli, orderType, comment).onEach { result->
            when(result){
                is Resource.Error ->{
                    _validateRequestData.value = GenericState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _validateRequestData.value = GenericState(isLoading = true)  }
                is Resource.Success ->{
                    _validateRequestData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }

    fun denyRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String){
        denyRequestInteractor(uid, cddeid, deli, orderType, comment).onEach { result->
            when(result){
                is Resource.Error ->{
                    _denyRequestData.value = GenericState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _denyRequestData.value = GenericState(isLoading = true)  }
                is Resource.Success ->{
                    _denyRequestData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }

    fun explainRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String){
        explainRequestInteractor(uid, cddeid, deli, orderType, comment).onEach { result->
            when(result){
                is Resource.Error ->{
                    _explainRequestData.value = GenericState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _explainRequestData.value = GenericState(isLoading = true)  }
                is Resource.Success ->{
                    _explainRequestData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}