package com.sergey.shuruta

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: Fragment(), ISettingsListener {

    private var listener: IMiddlewareListener? = null
    private var imageUri: Uri? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? IMiddlewareListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPhotoButton.setOnClickListener {
            listener?.openImage()
        }

        goButton.setOnClickListener {
            val lat = latEditText.text.toString().toDoubleOrNull()
            val lng = lngEditText.text.toString().toDoubleOrNull()
            if(lat != null && lng != null) {
                listener?.setPosition(LatLng(lat, lng), imageUri)
            }
        }
    }

    override fun onImageOpened(uri: Uri) {
        imageUri = uri
        photoImageView.setImageURI(uri)
    }
}