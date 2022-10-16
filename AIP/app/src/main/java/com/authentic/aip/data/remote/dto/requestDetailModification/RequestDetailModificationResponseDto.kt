package com.authentic.aip.data.remote.dto.requestDetailModification


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class RequestDetailModificationResponseDto(
    @SerializedName("data")
    val `data`: RequestDetailModificationDto?,
    @SerializedName("form_errors")
    val formErrors: FormErrorsDto?,
    @SerializedName("status")
    val status: StatusDto?
)