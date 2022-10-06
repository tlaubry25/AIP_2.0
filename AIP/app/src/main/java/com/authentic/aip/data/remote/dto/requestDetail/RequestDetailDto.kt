package com.authentic.aip.data.remote.dto.requestDetail

import com.authentic.aip.data.remote.dto.PageDataDto
    import com.authentic.aip.data.remote.dto.toPageData
import com.authentic.aip.domain.model.DedLine
import com.authentic.aip.domain.model.RequestDetail
import com.google.gson.annotations.SerializedName

data class RequestDetailDto(
    @SerializedName("listDedLine")
    val listDedLine: List<DedLineDto?>?,
    @SerializedName("pageData")
    val pageData: PageDataDto?
)
fun RequestDetailDto.toRequestDetail(): RequestDetail {
    val listDed : MutableList<DedLine> = mutableListOf()
    if(!listDedLine.isNullOrEmpty()){
        for(ded in listDedLine){
            val dedConverted = ded?.toDedLine()
            if(dedConverted!=null){
                listDed.add(dedConverted)
            }
        }
    }

    return RequestDetail(listDedLine = listDed, pageData = pageData?.toPageData())
}