package com.authentic.aip.data.remote.dto.listAttachments


import com.authentic.aip.domain.model.Attachments
import com.google.gson.annotations.SerializedName

data class AttachmentsDto(
    @SerializedName("docName")
    val docName: String?,
    @SerializedName("doct")
    val doct: Int?,
    @SerializedName("type")
    val type: String?
)

fun AttachmentsDto.toAttachements():Attachments{
    return Attachments(docName = docName, doct = doct, type = type)
}