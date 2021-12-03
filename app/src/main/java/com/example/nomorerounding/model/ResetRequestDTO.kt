package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResetRequestDTO(
    var email: String?,
    var loginId: String?
) : Parcelable