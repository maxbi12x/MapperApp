package com.example.mapperapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.utils.HelperObject
import com.example.mapperapp.databinding.AddImageRecyclerItemBinding
import com.example.mapperapp.models.ImageDetailsModel
import java.util.*


class AddImageAdapter(private val dataSet: List<ImageDetailsModel>,private val addImageClickListener: AddImageClickListener) :
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

                image.setImageBitmap(HelperObject.getBitmap(dataSet[position].imageUri.toString()))
                imageName.text = dataSet[position].title
                additionTime.text = HelperObject.getRelativeTime(Date(dataSet[position].timeAdded))
                markerCount.text = dataSet[position].markersCount.toString()
                deleteButton.setOnClickListener{ addImageClickListener.onAddImageDeleteClick(dataSet[position].imageId)}
                listItem.setOnClickListener{ addImageClickListener.onViewClick(dataSet[position].imageId)}
                editButton.setOnClickListener{addImageClickListener.onEditNameClicked(dataSet[position].imageId)}


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