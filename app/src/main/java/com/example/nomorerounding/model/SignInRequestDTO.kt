package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInRequestDTO (
    var loginId : String?,
    var password : String?
) : Parcelable