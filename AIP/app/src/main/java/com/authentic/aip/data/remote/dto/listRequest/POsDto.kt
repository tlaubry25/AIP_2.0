package com.authentic.aip.data.remote.dto.listRequest


import com.authentic.aip.domain.model.POs
import com.google.gson.annotations.SerializedName

data class POsDto(
    @SerializedName("buov")
    val buov: Boolean,
    @SerializedName("cddeid")
    val cddeid: String,
    @SerializedName("dedAmtLoc")
    val dedAmtLoc: Double,
    @SerializedName("dest")
    val dest: String,
    @SerializedName("locCur")
    val locCur: String,
    @SerializedName("objt")
    val objt: String,
    @SerializedName("orderType")
    val orderType: String,
    @SerializedName("requestType")
    val requestType: String,
    @SerializedName("rqstName")
    val rqstName: String,
    @SerializedName("stby")
    val stby: Boolean
)

fun POsDto.toPOs():POs{
    return POs(
        buov = buov,
        cddeid = cddeid,
        dedAmtLoc = dedAmtLoc,
        dest = dest,
        locCur = locCur,
        objt = objt,
        orderType = orderType,
        requestType = requestType,
        rqstName = rqstName,
        stby = stby
    )
}