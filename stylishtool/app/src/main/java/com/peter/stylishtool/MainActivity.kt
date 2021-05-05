package com.peter.stylishtool

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.UserDictionary
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.peter.stylishtool.MainViewModel
import com.peter.stylishtool.R
import com.peter.stylishtool.databinding.ActivityMainBinding


// A "projection" defines the columns that will be returned for each row
private val mProjection: Array<String> = arrayOf(
    UserDictionary.Words._ID,    // Contract class constant for the _ID column name
    UserDictionary.Words.WORD,   // Contract class constant for the word column name
    UserDictionary.Words.LOCALE  // Contract class constant for the locale column name
)

// Defines a string to contain the selection clause
private var selectionClause: String? = null

// Declares an array to contain selection arguments
private lateinit var selectionArgs: Array<String>


class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this, R.layout.activity_main
        )

        binding.viewModel= viewModel

//        binding.editText.doOnTextChanged { text, start, before, count ->
//            viewModel.id?.value =text.toString()
//            Log.d("Peter","${viewModel.id?.value}")
//        }
        val adapter=MainAdapter(viewModel)
        binding.recycler.adapter=adapter

        val cartItem = contentResolver.query(
            Uri.parse("content://app.appworks.school.stylish.data.contentprovider"),   // The content URI of the words table
            null,                        // The columns to return for each row
            null,                   // Selection criteria
            null,      // Selection criteria
            null                          // The sort order for the returned rows
        )

        val productList = ArrayList<Product>()

        viewModel.selectedProducts=productList
        binding.lifecycleOwner=this




        if (cartItem!!.moveToFirst()){
            while (!cartItem.isAfterLast) {
                val id = cartItem.getLong(cartItem.getColumnIndex("product_id"))
                val image = cartItem.getString(cartItem.getColumnIndex("product_main_image"))
//                Log.d("Peter","Image value = $image")
                productList.add(Product(id,image))
//                Log.d("Peter","Product value = ${Product(id,image)}")

                cartItem.moveToNext()
            }
        }
        cartItem.close()
        Log.d("Peter","List value = $productList")
        Log.d("Peter","viewModel value = ${viewModel.selectedProducts}")





        val intent = Intent(Intent.ACTION_VIEW,Uri.parse("example://product/id"))
        viewModel.id.observe(this, Observer {
            Log.d("idValue","value changed")
        })




        binding.button.setOnClickListener{
            Log.d("idValue","${viewModel.id.value}")

            intent.putExtra("Username", viewModel.id.value)
//            val shareIntent = Intent.createChooser(intent,"Username")
            startActivity(intent)
        }


    }

    // Queries the user dictionary and returns results











}