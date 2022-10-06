package com.authentic.aip.data.remote.dto.genericResponse


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class GenericResponseDto(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("form_errors")
    val formErrors: FormErrorsDto,
    @SerializedName("status")
    val status: StatusDto
)