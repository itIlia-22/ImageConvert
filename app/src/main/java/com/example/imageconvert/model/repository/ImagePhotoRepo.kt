package com.example.imageconvert.model.repository

import android.content.Context
import io.reactivex.rxjava3.core.Single

interface ImagePhotoRepo {
    fun getImage(context: Context):Single<List<String>>
}