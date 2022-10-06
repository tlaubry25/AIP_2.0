package com.authentic.aip.domain.model

data class ListRequest(
    val listPOs: List<POs?>?=null,
    val pageData: PageData? = null
)
