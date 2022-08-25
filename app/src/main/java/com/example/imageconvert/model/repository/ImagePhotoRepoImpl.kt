package com.example.imageconvert.model.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.rxjava3.core.Single

class ImagePhotoRepoImpl : ImagePhotoRepo {
    override fun getImage(context: Context): Single<List<String>> {
        return Single.create{
            val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor: Cursor
            val listAllImages = ArrayList<String>()
            var pathImages: String
            val projection =
                arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val orderBy = MediaStore.Video.Media.DATE_TAKEN
            cursor = context.contentResolver.query(uri, projection, null, null, "$orderBy DESC")!!
            val columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)


            while (cursor.moveToNext()) {
                pathImages = cursor.getString(columnIndexData)
                listAllImages.add(pathImages)
            }

            it.onSuccess(listAllImages)
        }



    }
}