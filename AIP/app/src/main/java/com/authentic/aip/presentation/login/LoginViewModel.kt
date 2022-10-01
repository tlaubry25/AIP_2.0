package com.authentic.aip.presentation.login

import androidx.lifecycle.*
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor : LoginInteractor
):ViewModel() {
    private val _loginData = MutableLiveData<LoginState>()
    val loginLiveData : LiveData<LoginState> = _loginData

    fun getLogin(login: String, password : String){
        loginInteractor(login, password).onEach { result->
            when(result){
                //TODO déterminer l'état du retour via un objet (exemple : LoginState dans le package : presentation/login)?
                is Resource.Error ->{ _loginData.value = LoginState(error = "testError") }
                is Resource.Loading ->{ _loginData.value = LoginState(isLoading = true)  }
                is Resource.Success ->{ _loginData.value = LoginState(loginObject = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}