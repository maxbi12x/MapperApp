package com.example.mapperapp.activity

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapperapp.AddImageAdapter
import com.example.mapperapp.databinding.ActivityAddImageBinding
import com.example.mapperapp.dialogs.AddImageDialogFragment
import com.example.mapperapp.dialogs.OnImageAdded
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.viewmodel.AddImageViewModel

class AddImageActivity : AppCompatActivity(),OnImageAdded,AddImageAdapter.AddImageClickListener {
    private var _binding : ActivityAddImageBinding? = null
    private val binding get() = _binding!!
    private var list = ArrayList<ImageDetailsModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddImageBinding.inflate(layoutInflater)
        AddImageViewModel(application)

        setAdapter()
        setListeners()
        setContentView(binding.root)
    }
    private fun setAdapter(){

        binding.recycler.layoutManager = LinearLayoutManager(this@AddImageActivity,LinearLayoutManager.VERTICAL,false)

        binding.recycler.adapter = AddImageAdapter(list,this)
        TransitionManager.beginDelayedTransition(binding.recycler, AutoTransition())
    }
    private  fun setListeners(){
        binding.addImage.setOnClickListener{
            AddImageDialogFragment.instance(this@AddImageActivity, isAdd = true).show(supportFragmentManager,"ADD_IMAGE")
        }
    }

    override fun getImageAdded(model: ImageDetailsModel) {
        list.add(0,model)
        binding.recycler.adapter = AddImageAdapter(list,this)

    }

    override fun onViewClick(position: Int) {
        startActivity(Intent(this@AddImageActivity, MainActivity::class.java))
    }


    override fun onAddImageDeleteClick(position: Int) {
        list.removeAt(position)
        binding.recycler.adapter = AddImageAdapter(list,this)
    }

    override fun onEditNameClicked(position: Int) {

        AddImageDialogFragment.instance(this@AddImageActivity,list[position].imageId,false).show(supportFragmentManager,"EDIT_IMAGE")
    }
}