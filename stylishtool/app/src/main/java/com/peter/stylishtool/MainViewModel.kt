package com.peter.stylishtool

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel (): ViewModel() {


    var selectedProducts: ArrayList<Product>? = null


    val id = MutableLiveData<String>()



}