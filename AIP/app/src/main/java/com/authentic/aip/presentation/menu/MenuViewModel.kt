package com.authentic.aip.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authentic.aip.common.Resource
import com.authentic.aip.domain.interactor.MenuInteractor
import com.authentic.aip.presentation.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuInteractor : MenuInteractor
):ViewModel(){
    private val _menuData = MutableLiveData<MenuState>()
    val menuLiveData : LiveData<MenuState> = _menuData

    fun getNbRequest(uid: String){
        menuInteractor(uid).onEach { result->
            when(result){
                is Resource.Error ->{ _menuData.value = MenuState(error = result.message?:"ErrorWebservice") }
                is Resource.Loading ->{ _menuData.value = MenuState(isLoading = true)  }
                is Resource.Success ->{ _menuData.value = MenuState(nbRequest = result.data) }
            }
        }.launchIn(viewModelScope)
    }
}