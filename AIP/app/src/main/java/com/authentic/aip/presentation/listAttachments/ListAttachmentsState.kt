package com.authentic.aip.presentation.listAttachments

import com.authentic.aip.domain.model.ListAttachments

data class ListAttachmentsState(
    val isLoading : Boolean = false,
    val error : String = "",
    val listAttachments : ListAttachments? = null
)
