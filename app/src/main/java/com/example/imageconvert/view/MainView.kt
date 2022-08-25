package com.example.imageconvert.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView: MvpView{
    fun initList(listPhoto:List<String>)
    fun checkPermission()
    fun showToast(message: String)
    fun showSnackBar(path: String)
    fun hideSnackBar()
    fun setClickRecycler(boolean: Boolean)
}