package com.authentic.aip.domain.model

data class RequestDetail (
    val bfidName: String,
    val budgAmtLoc: Double,
    val coce: String,
    val dedAmtLoc: Double,
    val dedAmtPur: Double,
    val dwdt: Int,
    val engaAmtLoc: Double,
    val faci: String,
    val locCur: String,
    val nbDocs: Int,
    val nbNotes: Int,
    val objt: String,
    val proj: String,
    val purCur: String,
    val sunm: String
)