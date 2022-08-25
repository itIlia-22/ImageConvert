package com.example.imageconvert.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imageconvert.R
import com.example.imageconvert.databinding.ActivityMainBinding
import com.example.imageconvert.model.repository.ImagePhotoRepo
import com.example.imageconvert.model.repository.ImagePhotoRepoImpl
import com.example.imageconvert.model.repository.OnItemClick
import com.example.imageconvert.presenter.MainPresenter
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var snackbar: Snackbar
    private var isRecycler = true
    private val adapter = AdapterPhoto(object : OnItemClick{
        override fun onImageItemClick(stringPath: String) {
            if (isRecycler) {
                isRecycler = false
                Log.d("Recycler", "click")
                presenter.convertImage(stringPath)
            }
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.cancel) {
                presenter.disposeConvert()
                isRecycler = true
            }
    }

    override fun initList(listPhoto: List<String>)= with(binding) {
        tvPhotos.text = "${resources.getString(R.string.add_photos)} ${listPhoto.size}"
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

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(path: String) {
        snackbar.setText("${resources.getString(R.string.converting)}\n$path")
        snackbar.show()
    }

    override fun hideSnackBar() {
        snackbar.dismiss()
        isRecycler = true
    }

    override fun setClickRecycler(boolean: Boolean) {
        binding.recyclerView.isClickable = boolean
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешен доступ к галерее", Toast.LENGTH_SHORT).show()
                presenter.loadImage()
            } else {
                Toast.makeText(this, "Нет доступа к галерее", Toast.LENGTH_SHORT).show()
            }
        }
    }
}