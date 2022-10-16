package com.authentic.aip.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Attachments(
    val docName: String?="",
    val doct: Int?=0,
    val type: String?=""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(docName)
        parcel.writeValue(doct)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attachments> {
        override fun createFromParcel(parcel: Parcel): Attachments {
            return Attachments(parcel)
        }

        override fun newArray(size: Int): Array<Attachments?> {
            return arrayOfNulls(size)
        }
    }
}