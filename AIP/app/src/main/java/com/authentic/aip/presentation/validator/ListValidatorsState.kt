package com.authentic.aip.presentation.validator

import com.authentic.aip.domain.model.ValidatorList

class ListValidatorsState(
    val isLoading : Boolean = false,
    val error : String = "",
    val listValidators : ValidatorList? = null
)