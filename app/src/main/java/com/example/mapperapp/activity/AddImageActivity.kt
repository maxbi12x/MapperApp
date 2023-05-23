package com.example.mapperapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapperapp.databinding.ActivityAddImageBinding
import com.example.mapperapp.dialogs.AddImageDialogFragment
import com.example.mapperapp.AddImageRecycler
import com.example.mapperapp.dialogs.OnImageAdded
import com.example.mapperapp.interfaces.DialogResponse
import com.example.mapperapp.models.ImageDetailsModel
import kotlin.collections.ArrayList

class AddImageActivity : AppCompatActivity(),OnImageAdded {
    private var _binding : ActivityAddImageBinding? = null
    private val binding get() = _binding!!
    private var list = ArrayList<ImageDetailsModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddImageBinding.inflate(layoutInflater)

        setAdapter()
        setListeners()


        setContentView(binding.root)

    }
    private fun setAdapter(){
        binding.recycler.layoutManager = LinearLayoutManager(this@AddImageActivity,LinearLayoutManager.VERTICAL,false)
        binding.recycler.adapter = AddImageRecycler(list)
    }
    private  fun setListeners(){
        binding.appName.setOnClickListener{
            startActivity(Intent(this@AddImageActivity, MainActivity::class.java))
        }
        binding.addImage.setOnClickListener{
            AddImageDialogFragment.instance(this@AddImageActivity).show(supportFragmentManager,"ADD_IMAGE")
        }
    }

    override fun getImageAdded(model: ImageDetailsModel) {
        list.add(0,model)
        binding.recycler.adapter = AddImageRecycler(list)

    }


}