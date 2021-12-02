package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpaceResponseDTO(
    var spaceColumn: Int?,
    var spaceRow: Int?,
    var userId: Int?
) : Parcelable