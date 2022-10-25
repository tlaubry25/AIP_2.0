package com.authentic.aip.presentation.generic

data class GenericState(
val isLoading : Boolean = false,
val isError : Boolean?=null,
val data : Any? = null
)

