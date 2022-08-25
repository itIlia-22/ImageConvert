package com.example.imageconvert.presenter

import android.content.Context
import com.example.imageconvert.model.repository.ImagePhotoRepo
import com.example.imageconvert.view.MainView
import moxy.MvpPresenter

class MainPresenter (
    val context: Context,
    val photoRepo: ImagePhotoRepo
):MvpPresenter<MainView>(){
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.checkPermission()
    }
fun loadImage(){
    val data = photoRepo.getImage(context)
    viewState.initList(data)
}
}