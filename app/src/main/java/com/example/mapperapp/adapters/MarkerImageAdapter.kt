package com.example.mapperapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.databinding.ImageItemBinding
import com.example.mapperapp.models.MarkerImagesModel
import com.example.mapperapp.utils.HelperObject

class MarkerImageAdapter(private val dataSet: List<MarkerImagesModel>) :
    RecyclerView.Adapter<MarkerImageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            binding.image.setImageBitmap(HelperObject.getBitmap(dataSet[position].imageUri.toString()))
        }
    }
    override fun getItemCount() = dataSet.size
}