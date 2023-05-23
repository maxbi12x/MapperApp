package com.example.mapperapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.databinding.AddImageRecyclerItemBinding
import com.example.mapperapp.models.ImageDetailsModel
import java.util.*
import kotlin.collections.ArrayList


class AddImageRecycler(private val dataSet: ArrayList<ImageDetailsModel>) :
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
                imageName.text = dataSet[position].title
                additionTime.text = HelperObject.getRelativeTime(Date(dataSet[position].timeAdded))
                markerCount.text = dataSet[position].markersCount.toString()

            }

        }
    }

    override fun getItemCount() = dataSet.size

}