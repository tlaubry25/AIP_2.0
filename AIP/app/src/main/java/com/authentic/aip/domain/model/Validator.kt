package com.authentic.aip.domain.model

data class Validator(
    val user: String?="",
    val userName: String?=""
)
{
    override fun toString(): String {
        return userName?:""
    }
}