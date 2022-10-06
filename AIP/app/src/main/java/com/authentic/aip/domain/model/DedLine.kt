package com.authentic.aip.domain.model


data class DedLine(
    val cucd: String,
    val deli: Int,
    val existText: Boolean,
    val itds: String,
    val lineType: String,
    val lnam: Double,
    val nbDocs: Int,
    val nbNotes: Int,
    val ppqt: Int,
    val pupr: Double,
    val requestType: String
)