package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StringResponseDTO(
    var message: String?
) : Parcelable