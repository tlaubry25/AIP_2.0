package com.authentic.aip.presentation.documentViewer

data class GetAttachmentState(
    val isLoading : Boolean = false,
    val error : String = "",
    val attachmentData : String? = null
)
