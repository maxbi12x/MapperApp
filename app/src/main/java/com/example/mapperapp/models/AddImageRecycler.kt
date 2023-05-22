package com.example.mapperapp.models

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.R
import com.example.mapperapp.databinding.ActivityAddImageBinding
import com.example.mapperapp.databinding.AddImageRecyclerItemBinding


class AddImageRecycler(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<AddImageRecycler.ViewHolder>() {

    inner class ViewHolder(val binding : AddImageRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddImageRecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        Log.e("DATA SET", dataSet.size.toString())
        with(viewHolder){
            binding.apply {
                image.setImageResource(R.drawable._20623)
                imageName.text = dataSet[position]
            }

        }
    }

    override fun getItemCount() = dataSet.size

}