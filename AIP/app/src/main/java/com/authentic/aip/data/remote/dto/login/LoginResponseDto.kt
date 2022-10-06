package com.authentic.aip.data.remote.dto.login


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("data")
    val `data`: LoginDto,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)