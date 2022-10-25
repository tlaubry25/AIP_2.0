package com.authentic.aip.presentation.listRequest

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.R
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.ListRequestInteractor
import com.authentic.aip.presentation.MessageManager
import com.authentic.aip.presentation.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListRequestViewModel @Inject constructor(
    private val listRequestInteractor : ListRequestInteractor
): ViewModel() {
    private val _listRequestData = MutableLiveData<ListRequestState>()
    val listRequestLiveData : LiveData<ListRequestState> = _listRequestData

    fun listRequest(context: Context, uid : String, orderType : Char, histo:Int, numPg:Int){
        listRequestInteractor(uid, orderType, histo, numPg).onEach { result->
            when(result){
                is Resource.Error ->{ _listRequestData.value = ListRequestState(error = "testError")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _listRequestData.value = ListRequestState(isLoading = true)  }
                is Resource.Success ->{ _listRequestData.value = ListRequestState(listRequest = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}