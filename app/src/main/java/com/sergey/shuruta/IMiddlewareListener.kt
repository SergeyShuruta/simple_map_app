package com.sergey.shuruta

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

interface IMiddlewareListener {
    fun openImage()
    fun setPosition(position: LatLng, imageUri: Uri?)
}