package com.example.mapperapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapperapp.databinding.AddImageRecyclerItemBinding
import com.example.mapperapp.databinding.MarkerListItemBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.utils.HelperObject
import java.util.*

class MarkerAdapter(private val dataSet: List<MarkerModel>) :
    RecyclerView.Adapter<MarkerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MarkerListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = MarkerListItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            binding.apply {
                title.text = dataSet[position].title
                nameText.text = dataSet[position].person
                discussionText.text = dataSet[position].discussion
            }

        }
    }

    override fun getItemCount() = dataSet.size

}