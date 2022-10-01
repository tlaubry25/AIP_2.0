package com.authentic.aip.presentation.login

import androidx.lifecycle.*
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.LoginInteractor
import com.authentic.aip.domain.model.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor : LoginInteractor
):ViewModel() {
    private val _loginData = MutableLiveData<Login>()
    private val loginLiveData : LiveData<Login> = _loginData

    fun getLoginData():LiveData<Login>{
        return loginLiveData
    }

    fun getLogin(login: String, password : String){
        loginInteractor(login, password).onEach { result->
            when(result){
                //TODO déterminer l'état du retour via un objet (exemple : LoginState dans le package : presentation/login)?
                is Resource.Error ->{ _loginData.value = result.data }
                is Resource.Loading ->{ _loginData.value = result.data  }
                is Resource.Success ->{ _loginData.value = result.data }
            }
        }.launchIn(viewModelScope)
    }
}