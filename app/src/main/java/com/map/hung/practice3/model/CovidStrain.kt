// CovidStrain.kt
package com.map.hung.practice3.model

import android.os.Parcel
import android.os.Parcelable

data class CovidStrain(
    var id: Int? = null,
    var virusName: String? = null,
    var structure: List<String>? = null,
    var appearanceDate: String? = null,
    var vaccineStatus: Boolean = false,
    var worldInfectionCount: Int? = null,
    var vietnamInfectionCount: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readValue(Int::class.java.classLoader) as? Int,
        virusName = parcel.readString(),
        structure = parcel.createStringArrayList(),
        appearanceDate = parcel.readString(),
        vaccineStatus = parcel.readByte() != 0.toByte(),
        worldInfectionCount = parcel.readValue(Int::class.java.classLoader) as? Int,
        vietnamInfectionCount = parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(virusName)
        parcel.writeStringList(structure)
        parcel.writeString(appearanceDate)
        parcel.writeByte(if (vaccineStatus) 1 else 0)
        parcel.writeValue(worldInfectionCount)
        parcel.writeValue(vietnamInfectionCount)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CovidStrain> {
        override fun createFromParcel(parcel: Parcel): CovidStrain = CovidStrain(parcel)
        override fun newArray(size: Int): Array<CovidStrain?> = arrayOfNulls(size)
    }
}
