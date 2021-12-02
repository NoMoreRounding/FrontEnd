package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenResponseDTO(
    var accessToken: String?,
    var refreshToken: String?
) : Parcelable