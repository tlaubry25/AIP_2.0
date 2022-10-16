package com.authentic.aip.presentation.requestDetailModification

import com.authentic.aip.domain.model.RequestDetailModification

data class RequestDetailModificationState(
    val isLoading : Boolean = false,
    val error : String = "",
    val requestModificationData : RequestDetailModification? = null
)
