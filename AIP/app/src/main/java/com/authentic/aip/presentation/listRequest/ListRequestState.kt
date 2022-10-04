package com.authentic.aip.presentation.listRequest

import com.authentic.aip.domain.model.ListRequest

class ListRequestState(
    val isLoading : Boolean = false,
    val error : String = "",
    val listRequest : ListRequest? = null
)