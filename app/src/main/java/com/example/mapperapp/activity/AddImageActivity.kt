package com.example.mapperapp.activity

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.databinding.ActivityAddImageBinding
import com.example.mapperapp.dialogs.AddImageDialogFragment
import com.example.mapperapp.models.AddImageRecycler
import java.util.*
import kotlin.collections.ArrayList

class AddImageActivity : AppCompatActivity() {
    private var _binding : ActivityAddImageBinding? = null
    private val binding get() = _binding!!
    private var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddImageBinding.inflate(layoutInflater)
        binding.appName.setOnClickListener{
            startActivity(Intent(this@AddImageActivity, MainActivity::class.java))
        }
//        startActivity(Intent(this@AddImageActivity, MainActivity::class.java))
        list.addAll(listOf("arr","w","adad","adad","adad"))
        binding.recycler.layoutManager = LinearLayoutManager(this@AddImageActivity,LinearLayoutManager.VERTICAL,false)
        binding.recycler.adapter = AddImageRecycler(list)


//        Log.e("NOT WORKING",binding.recycler.adapter!!.itemCount.toString())
        binding.apply {

//            recycler.layoutManager = LinearLayoutManager(this@AddImageActivity,LinearLayoutManager.VERTICAL,false)
//            recycler.adapter = AddImageRecycler(ArrayList<String>(10).toTypedArray())

            addImage.setOnClickListener{
//                startActivity(Intent(this@AddImageActivity, MainActivity::class.java))
                AddImageDialogFragment.instance().show(supportFragmentManager,"ADD_IMAGE")
            }
        }
        setContentView(binding.root)

    }
}