package com.authentic.aip.data.remote.dto.listRequest


import com.authentic.aip.domain.model.PageData
import com.google.gson.annotations.SerializedName

data class PageDataDto(
    @SerializedName("nbPg")
    val nbPg: Int,
    @SerializedName("nbRes")
    val nbRes: Int
)

fun PageDataDto.toPageData(): PageData {
    return PageData(nbPg = nbPg, nbRes = nbRes)
}