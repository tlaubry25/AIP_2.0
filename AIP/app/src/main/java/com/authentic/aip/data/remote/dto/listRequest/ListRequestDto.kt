package com.authentic.aip.data.remote.dto.listRequest

import com.authentic.aip.data.remote.dto.PageDataDto
import com.authentic.aip.data.remote.dto.toPageData
import com.authentic.aip.domain.model.ListRequest
import com.authentic.aip.domain.model.POs
import com.google.gson.annotations.SerializedName

data class ListRequestDto(
    @SerializedName("listPOs")
    val listPOs: List<POsDto?>?=null,
    @SerializedName("pageData")
    val pageData: PageDataDto?
)

fun ListRequestDto.toListRequest():ListRequest{
    val listPO : MutableList<POs> = mutableListOf()
    if(!listPOs.isNullOrEmpty()){
        for(po in listPOs){
            val poConverted = po?.toPOs()
            if(poConverted!=null){
                listPO.add(poConverted)
            }
        }
    }

    return ListRequest(listPOs = listPO, pageData = pageData?.toPageData())
}