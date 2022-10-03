package com.authentic.aip.presentation.menu

data class MenuState(
    val isLoading : Boolean = false,
    val error : String = "",
    val nbRequest : Int? = null
)
