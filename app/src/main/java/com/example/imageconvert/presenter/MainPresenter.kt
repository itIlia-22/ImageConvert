package com.example.imageconvert.presenter

import android.content.Context
import android.util.Log
import com.bumptech.glide.load.model.ByteArrayLoader
import com.example.imageconvert.Convert
import com.example.imageconvert.Convert.convertImage
import com.example.imageconvert.model.repository.ImagePhotoRepo
import com.example.imageconvert.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class MainPresenter (
    val context: Context,
    val photoRepo: ImagePhotoRepo
):MvpPresenter<MainView>(){
    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.checkPermission()
    }
fun loadImage(){
    photoRepo.getImage(context)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                viewState.initList(it)
            },
            {
                Log.d("getImage", "${it.message}")
            }
        )
}

    fun convertImage(path: String) {
        disposable = convertImage(context, path)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                Convert.saveImage(context, it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        loadImage()
                        viewState.hideSnackBar()
                    }
                    .subscribe()
            }
            .doOnError {
                Log.d("ConvertSingle", "${it.message}")
                viewState.hideSnackBar()
            }
            .subscribe()
        viewState.showSnackBar(path)
    }

    fun disposeConvert() {
        disposable?.dispose()
    }
}