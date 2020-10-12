package com.sergey.shuruta

import android.net.Uri

interface ISettingsListener {
    fun onImageOpened(uri: Uri)
}