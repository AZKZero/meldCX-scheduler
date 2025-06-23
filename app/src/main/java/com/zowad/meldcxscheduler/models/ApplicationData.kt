package com.zowad.meldcxscheduler.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Parcelize
@Serializable
data class ApplicationData(
    @IgnoredOnParcel @Transient var icon: Drawable? = null,
    var name: String,
    val packageName: String,
) : Parcelable