package com.authentic.aip.data.remote.dto.listAttachments


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class ListAttachmentsResponseDto(
    @SerializedName("data")
    val `data`: ListAttachmentsDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)