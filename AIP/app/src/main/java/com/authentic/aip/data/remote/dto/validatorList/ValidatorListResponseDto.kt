package com.authentic.aip.data.remote.dto.validatorList


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class ValidatorListResponseDto(
    @SerializedName("data")
    val `data`: ValidatorListDto,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)