package com.authentic.aip.domain.model

data class RequestDetailModification(
    val itds: String?,
    val itdsOld: String?,
    val lnam: Double?,
    val lnamOld: Double?,
    val ppqt: Int?,
    val ppqtOld: Int?,
    val pupr: Double?,
    val puprOld: Double?
)