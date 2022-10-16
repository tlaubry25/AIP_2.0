package com.authentic.aip.data.remote.dto.requestDetailModification


import com.authentic.aip.domain.model.RequestDetailModification
import com.google.gson.annotations.SerializedName

data class RequestDetailModificationDto(
    @SerializedName("itds")
    val itds: String?,
    @SerializedName("itdsOld")
    val itdsOld: String?,
    @SerializedName("lnam")
    val lnam: Double?,
    @SerializedName("lnamOld")
    val lnamOld: Double?,
    @SerializedName("ppqt")
    val ppqt: Int?,
    @SerializedName("ppqtOld")
    val ppqtOld: Int?,
    @SerializedName("pupr")
    val pupr: Double?,
    @SerializedName("puprOld")
    val puprOld: Double?
)
fun RequestDetailModificationDto.toRequestDetailModification():RequestDetailModification{
    return RequestDetailModification(itds = itds, itdsOld = itdsOld, lnam = lnam, lnamOld = lnamOld, ppqt = ppqt, ppqtOld = ppqtOld, pupr= pupr, puprOld = puprOld)
}