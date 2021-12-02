package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpRequestDTO(
    var birth: String?,
    var carNumber: String,
    var compactCar: Boolean,
    var disabled: Boolean,
    var electric: Boolean,
    var email:	String,
    var gender:	String,
    var loginId: String,
    var name: String,
    var password: String,
    var pregnant: Boolean
) : Parcelable