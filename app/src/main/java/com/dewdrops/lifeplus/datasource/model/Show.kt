package com.dewdrops.lifeplus.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Show(
    val score: Double,
    val show: @RawValue ShowX
) : Parcelable