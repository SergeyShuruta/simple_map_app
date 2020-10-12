package com.sergey.shuruta

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

interface IMapListener {
    fun createMarker(position: LatLng, imageUri: Uri?)
}