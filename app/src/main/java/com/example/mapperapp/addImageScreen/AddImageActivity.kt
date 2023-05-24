package com.example.mapperapp.addImageScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapperapp.adapters.AddImageAdapter
import com.example.mapperapp.markerMainScreen.MainActivity
import com.example.mapperapp.databinding.ActivityAddImageBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.viewmodel.AddImageViewModel

class AddImageActivity : AppCompatActivity(), AddImageAdapter.AddImageClickListener {
    private var _binding : ActivityAddImageBinding? = null
    private val binding get() = _binding!!
    private var list = ArrayList<ImageDetailsModel>()
    private lateinit var viewModel : AddImageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddImageBinding.inflate(layoutInflater)
        viewModel = AddImageViewModel(application)

        setAdapter()
        setListeners()
        setLiveData()
        setContentView(binding.root)
    }
    private fun setAdapter(){

        binding.recycler.layoutManager = LinearLayoutManager(this@AddImageActivity,LinearLayoutManager.VERTICAL,false)

    }
    private  fun setListeners(){
        binding.addImage.setOnClickListener{
            AddImageDialogFragment.instance(isAdd = true).show(supportFragmentManager,"ADD_IMAGE")
        }
    }
    private fun setLiveData(){
        viewModel.imagesList()
        viewModel.imagesListLive.observe(this){
            binding.recycler.adapter = AddImageAdapter(it,this)
        }
    }



    override fun onViewClick(Id: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("IMAGE_ID", Id)
        startActivity(intent)
    }


    override fun onAddImageDeleteClick(id : Int) {
        viewModel.deleteImage(id)
    }

    override fun onEditNameClicked(id: Int) {
        AddImageDialogFragment.instance(id,false).show(supportFragmentManager,"EDIT_IMAGE")
    }
}