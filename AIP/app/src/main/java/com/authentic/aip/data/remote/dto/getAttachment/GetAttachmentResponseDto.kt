package com.authentic.aip.data.remote.dto.getAttachment


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class GetAttachmentResponseDto(
    @SerializedName("data")
    val `data`: String?,
    @SerializedName("form_errors")
    val formErrors: FormErrorsDto?,
    @SerializedName("status")
    val status: StatusDto?
)