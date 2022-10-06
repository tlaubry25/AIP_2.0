package com.authentic.aip.presentation.request

import com.authentic.aip.domain.model.Request

class RequestState(
    val isLoading : Boolean = false,
    val error : String = "",
    val requestData : Request? = null
)