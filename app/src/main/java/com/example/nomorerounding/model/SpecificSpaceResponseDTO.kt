package com.example.nomorerounding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecificSpaceResponseDTO(
    var spaceColumn: Int?,
    var spaceRow: Int?,
    var spaceType: String?
) : Parcelable