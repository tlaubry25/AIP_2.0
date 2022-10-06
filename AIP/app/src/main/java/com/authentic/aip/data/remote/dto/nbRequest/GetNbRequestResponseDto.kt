package com.authentic.aip.data.remote.dto.nbRequest


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class GetNbRequestResponseDto(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)