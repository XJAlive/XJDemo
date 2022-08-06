package com.xj.demo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Banner(var desc: String, var id: Int, var imagePath: String? = null) : Parcelable