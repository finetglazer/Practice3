package com.map.hung.practice3.model

import android.os.Parcel
import android.os.Parcelable

class Course(
    var id: Int? = null,
    var name: String? = null,
    var startDate: String? = null,
    var major: String? = null,
    var isActive: Boolean = false,
    var fee: Int? = null
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        id = parcel.readValue(Int::class.java.classLoader) as? Int,
        name = parcel.readString(),
        startDate = parcel.readString(),
        major = parcel.readString(),
        isActive = parcel.readByte() != 0.toByte(),
        fee = parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(startDate)
        parcel.writeString(major)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeValue(fee)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}
