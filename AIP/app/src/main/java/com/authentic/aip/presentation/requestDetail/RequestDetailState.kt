package com.authentic.aip.presentation.requestDetail

import com.authentic.aip.domain.model.RequestDetail

class RequestDetailState(
    val isLoading : Boolean = false,
    val error : String = "",
    val requestDetailData : RequestDetail? = null
) {
}