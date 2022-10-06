package com.authentic.aip.presentation.documentViewer

data class AttachmentState(
val isLoading : Boolean = false,
val error : String = "",
val attachmentData : Any? = null
)

