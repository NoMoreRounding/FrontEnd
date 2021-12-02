package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarResponseDTO(
    var carNumber: String?,
    var pregnant: Boolean?,
    var compactCar: Boolean?,
    var electric: Boolean?,
    var disabled: Boolean?
) : Parcelable