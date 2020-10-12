package com.sergey.shuruta

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.FileDescriptor
import java.io.IOException
import java.lang.Exception

class MapFragment: Fragment(), OnMapReadyCallback, IMapListener {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun createMarker(position: LatLng, imageUri: Uri?) {
        val cameraPosition = CameraPosition.Builder()
            .target(position)
            .zoom(17f)
            .build()
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.animateCamera(cu)

        imageUri?.let { uri ->
            var btmDescriptor: BitmapDescriptor? = null
            try {
                btmDescriptor = BitmapDescriptorFactory.fromBitmap(getBitmapFromUri(uri))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            btmDescriptor?.let {
                mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .icon(it)
                        .title("Marker")
                )
            }
        }
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        activity?.contentResolver?.openFileDescriptor(uri, "r")?.let { parcelFileDescriptor ->
            val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
            val bigImage: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            var newWidth = IMAGE_WIDTH
            var newHeight = IMAGE_HEIGHT
            if(bigImage.width > bigImage.height) {
                newHeight = (bigImage.height.toFloat() / bigImage.width.toFloat() * IMAGE_WIDTH).toInt()
            } else {
                newWidth = (bigImage.width.toFloat() / bigImage.height.toFloat() * IMAGE_HEIGHT).toInt()
            }
            val image:Bitmap = Bitmap.createScaledBitmap(bigImage, newWidth, newHeight, false)
            parcelFileDescriptor.close()
            return image
        }
        return null
    }

    companion object {
        private const val IMAGE_WIDTH = 100
        private const val IMAGE_HEIGHT = 100
    }

}