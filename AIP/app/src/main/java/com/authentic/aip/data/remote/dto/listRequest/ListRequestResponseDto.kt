package com.authentic.aip.data.remote.dto.listRequest


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class ListRequestResponseDto(
    @SerializedName("data")
    val `data`: ListRequestDto,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)