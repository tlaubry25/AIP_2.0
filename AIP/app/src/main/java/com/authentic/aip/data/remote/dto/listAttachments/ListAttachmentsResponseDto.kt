package com.authentic.aip.data.remote.dto.listAttachments


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class ListAttachmentsResponseDto(
    @SerializedName("data")
    val `data`: ListAttachmentsDto,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)