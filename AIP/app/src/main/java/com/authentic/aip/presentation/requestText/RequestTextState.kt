package com.authentic.aip.presentation.requestText

data class RequestTextState(
    val isLoading : Boolean = false,
    val error : String = "",
    val requestText : String? = null
)
