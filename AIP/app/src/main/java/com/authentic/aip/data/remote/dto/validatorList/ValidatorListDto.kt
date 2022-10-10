package com.authentic.aip.data.remote.dto.validatorList


import com.authentic.aip.data.remote.dto.PageDataDto
import com.authentic.aip.data.remote.dto.toPageData
import com.authentic.aip.domain.model.Validator
import com.authentic.aip.domain.model.ValidatorList
import com.google.gson.annotations.SerializedName

data class ValidatorListDto(
    @SerializedName("listValidators")
    val listValidators: List<ValidatorDto?>?,
    @SerializedName("pageData")
    val pageData: PageDataDto
)

fun ValidatorListDto.toValidatorList():ValidatorList{
    val listValidator : MutableList<Validator> = mutableListOf()
    if(!listValidators.isNullOrEmpty()){
        for(validator in listValidators){
            val validatorConverted = validator?.toValidator()
            if(validatorConverted!=null){
                listValidator.add(validatorConverted)
            }
        }
    }
    return ValidatorList(listValidators = listValidator, pageData = pageData?.toPageData())

}