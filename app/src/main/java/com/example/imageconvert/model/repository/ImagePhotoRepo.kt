package com.example.imageconvert.model.repository

import android.content.Context

interface ImagePhotoRepo {
    fun getImage(context: Context):List<String>
}