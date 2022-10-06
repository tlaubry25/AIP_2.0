package com.authentic.aip.data.remote.dto.requestDetail


import com.authentic.aip.domain.model.DedLine
import com.google.gson.annotations.SerializedName

data class DedLineDto(
    @SerializedName("cucd")
    val cucd: String,
    @SerializedName("deli")
    val deli: Int,
    @SerializedName("existText")
    val existText: Boolean,
    @SerializedName("itds")
    val itds: String,
    @SerializedName("lineType")
    val lineType: String,
    @SerializedName("lnam")
    val lnam: Double,
    @SerializedName("nbDocs")
    val nbDocs: Int,
    @SerializedName("nbNotes")
    val nbNotes: Int,
    @SerializedName("ppqt")
    val ppqt: Int,
    @SerializedName("pupr")
    val pupr: Double,
    @SerializedName("requestType")
    val requestType: String
)

fun DedLineDto.toDedLine(): DedLine {
    return DedLine(
        cucd = cucd,
        deli = deli,
        existText = existText,
        itds = itds,
        lineType = lineType,
        lnam = lnam,
        nbDocs = nbDocs,
        nbNotes = nbNotes,
        ppqt = ppqt,
        pupr = pupr,
        requestType = requestType)
}