package com.authentic.aip.domain.model

data class POs(
    val buov: Boolean,
    val cddeid: String,
    val dedAmtLoc: Double,
    val dest: String,
    val locCur: String,
    val objt: String,
    val orderType: String,
    val requestType: String,
    val rqstName: String,
    val stby: Boolean
)
