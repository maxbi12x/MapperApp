package com.example.mapperapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.databinding.AddImageRecyclerItemBinding
import com.example.mapperapp.models.ImageDetailsModel
import java.util.*
import kotlin.collections.ArrayList


class AddImageAdapter(private val dataSet: ArrayList<ImageDetailsModel>,private val addImageClickListener: AddImageClickListener) :
    RecyclerView.Adapter<AddImageAdapter.ViewHolder>() {

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
                deleteButton.setOnClickListener{ addImageClickListener.onAddImageDeleteClick(position)}
                listItem.setOnClickListener{ addImageClickListener.onViewClick(position)}
                editButton.setOnClickListener{addImageClickListener.onEditNameClicked(position)}


            }

        }
    }

    override fun getItemCount() = dataSet.size

    interface AddImageClickListener{
        fun onViewClick(position: Int)
        fun onAddImageDeleteClick(position: Int)
        fun onEditNameClicked(position: Int)
    }

}