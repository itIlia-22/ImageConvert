package com.example.imageconvert.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imageconvert.R
import com.example.imageconvert.databinding.ActivityMainBinding
import com.example.imageconvert.model.repository.ImagePhotoRepo
import com.example.imageconvert.model.repository.ImagePhotoRepoImpl
import com.example.imageconvert.model.repository.OnItemClick
import com.example.imageconvert.presenter.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

const val READ_EXTERNAL_STORAGE = 999
class MainActivity : MvpAppCompatActivity(),MainView{
    private lateinit var binding: ActivityMainBinding
    private val presenter by moxyPresenter {
        MainPresenter(
            this,ImagePhotoRepoImpl()
        )
    }

    private val adapter = AdapterPhoto(object : OnItemClick{
        override fun onImageItemClick(stringPath: String) {
            Toast.makeText(this@MainActivity,stringPath,Toast.LENGTH_LONG).show()
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initList(listPhoto: List<String>)= with(binding) {
        tvPhotos.append(" ${listPhoto.size}")
        recyclerView.adapter = adapter
        adapter.setData(listPhoto)
    }

    override fun checkPermission()  {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                READ_EXTERNAL_STORAGE
            )
        } else {
            presenter.loadImage()
        }
    }
}