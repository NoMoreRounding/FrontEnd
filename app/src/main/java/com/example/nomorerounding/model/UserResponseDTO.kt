package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponseDTO(
    var name: String?,
    var birth: String?,
    var gender: String?,
    var email: String?,
    var carResponse: CarResponseDTO?,
    var tokenResponse: TokenResponseDTO?,
    var id: Int?
) : Parcelable