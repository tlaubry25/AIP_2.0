package com.authentic.aip.data.remote.dto.validatorList


import com.authentic.aip.domain.model.Validator
import com.google.gson.annotations.SerializedName

data class ValidatorDto(
    @SerializedName("user")
    val user: String?,
    @SerializedName("userName")
    val userName: String?
)

fun ValidatorDto.toValidator():Validator{
    return Validator(user = user, userName = userName)
}