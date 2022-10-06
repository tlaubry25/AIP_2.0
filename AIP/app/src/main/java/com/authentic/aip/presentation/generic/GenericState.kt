package com.authentic.aip.presentation.generic

data class GenericState(
val isLoading : Boolean = false,
val error : String = "",
val data : Any? = null
)

