package com.authentic.aip.data.remote.dto.listAttachments


import com.authentic.aip.data.remote.dto.PageDataDto
import com.authentic.aip.data.remote.dto.toPageData
import com.authentic.aip.domain.model.Attachments
import com.authentic.aip.domain.model.ListAttachments
import com.google.gson.annotations.SerializedName

data class ListAttachmentsDto(
    @SerializedName("listAttachments")
    val listAttachments: List<AttachmentsDto?>,
    @SerializedName("pageData")
    val pageData: PageDataDto
)

fun ListAttachmentsDto.toListAttachments():ListAttachments{
    var mutablelistAttachments = mutableListOf<Attachments>()
    for(attachment in listAttachments){
        if(attachment!=null){
            attachment?.toAttachements()?.let { mutablelistAttachments.add(it) }
        }
    }

    return ListAttachments(listAttachments = mutablelistAttachments, pageData = pageData.toPageData())
}