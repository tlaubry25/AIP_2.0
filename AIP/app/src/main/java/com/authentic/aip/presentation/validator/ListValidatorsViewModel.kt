package com.authentic.aip.presentation.validator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.ListValidatorsInteractor
import com.authentic.aip.domain.interactor.UpdateValidatorInteractor
import com.authentic.aip.presentation.generic.GenericState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListValidatorsViewModel @Inject constructor(
    private val listValidatorsInteractor : ListValidatorsInteractor,
    private val validatorUpdateInteractor : UpdateValidatorInteractor
): ViewModel(){
    private val _listValidatorsData = MutableLiveData<ListValidatorsState>()
    val listValidatorsLiveData : LiveData<ListValidatorsState> = _listValidatorsData

    private val _updateValidatorData = MutableLiveData<GenericState>()
    val updateValidatorLiveData : LiveData<GenericState> = _updateValidatorData

    fun getListValidators(uid : String, cddeid : String?, numPg:Int){
        listValidatorsInteractor(uid, cddeid, numPg).onEach { result->
            when(result){
                is Resource.Error ->{
                    _listValidatorsData.value = ListValidatorsState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _listValidatorsData.value = ListValidatorsState(isLoading = true)  }
                is Resource.Success ->{
                    _listValidatorsData.value = ListValidatorsState(listValidators = result.data) }
            }
        }.launchIn(viewModelScope)
    }

    fun updateValidator(uid : String, cddeid : String, deli:Int, valr:String, comment:String){
        validatorUpdateInteractor(uid, cddeid, deli, valr, comment).onEach { result->
            when(result){
                is Resource.Error ->{
                    _updateValidatorData.value = GenericState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{
                    _updateValidatorData.value = GenericState(isLoading = true)  }
                is Resource.Success ->{
                    _updateValidatorData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}