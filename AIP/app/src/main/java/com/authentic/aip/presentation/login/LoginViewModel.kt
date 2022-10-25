package com.authentic.aip.presentation.login

import android.content.Context
import androidx.lifecycle.*
import com.authentic.aip.R
import com.authentic.aip.common.Constant
import com.authentic.aip.common.Resource
import com.authentic.aip.di.AppModule
import com.authentic.aip.di.HostSelectionInterceptor
import com.authentic.aip.domain.interactor.LoginInteractor
import com.authentic.aip.domain.interactor.RegisterDeviceInteractor
import com.authentic.aip.domain.interactor.VerifyUrlInteractor
import com.authentic.aip.presentation.MessageManager
import com.authentic.aip.presentation.generic.GenericState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor : LoginInteractor,
    private val registerDeviceInteractor: RegisterDeviceInteractor,
    private val verifyUrlInteractor: VerifyUrlInteractor
):ViewModel() {

    private val _loginData = MutableLiveData<LoginState>()
    val loginLiveData : LiveData<LoginState> = _loginData

    fun getLogin(context: Context, login: String, password : String, language:String, url:String, port:String){
        Constant.BASE_URL = url
        AppModule.interceptorHost.setHost(Constant.BASE_URL)
        AppModule.interceptorHost.setPort(port.toInt())
        loginInteractor(login, password, language).onEach { result->
            when(result){
                is Resource.Error ->{
                    _loginData.value = LoginState(error = "testError")
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _loginData.value = LoginState(isLoading = true)  }
                is Resource.Success ->{ _loginData.value = LoginState(loginObject = result.data) }
            }
        }.launchIn(viewModelScope)
    }

    private val _registerDeviceData = MutableLiveData<GenericState>()
    val registerDeviceLiveData : LiveData<GenericState> = _registerDeviceData

    fun registerDevice(context: Context, uid: String, type: Char, did: String){
        registerDeviceInteractor(uid, type, did).onEach { result->
            when(result){
                is Resource.Error ->{ _registerDeviceData.value = GenericState(isError = true)
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Loading ->{ _registerDeviceData.value = GenericState(isLoading = true)  }
                is Resource.Success ->{ _registerDeviceData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }
    private val _verifyUrlData = MutableLiveData<GenericState>()
    val verifyUrlLiveData : LiveData<GenericState> = _verifyUrlData

    fun verifyUrl(context: Context, url:String, port:String){
        /*Constant.BASE_URL = url+":"+port+Constant.URL_END*/
        Constant.BASE_URL = url
        AppModule.interceptorHost.setHost(Constant.BASE_URL)
        AppModule.interceptorHost.setPort(port.toInt())
        verifyUrlInteractor().onEach { result->
            when(result){
                is Resource.Loading ->{ _verifyUrlData.value = GenericState(isLoading = true)  }
                is Resource.Error ->{
                    _verifyUrlData.value = GenericState(isError = true)
                    MessageManager.showToast(context, R.string.login_ws_error)
                }
                is Resource.Success ->{ _verifyUrlData.value = GenericState(data = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}