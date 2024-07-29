package com.kuma.kangaroof.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val token:String?=""):Parcelable
