package com.example.mapperapp.markerMainScreen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mapperapp.databinding.BottomSheetDialogBinding
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.utils.HelperObject
import com.example.mapperapp.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException


class BottomSheet : BottomSheetDialogFragment(),HelperObject.DeleteItem {

    private var markerModel: MarkerModel? = null
    private var markerID: Int = -1
    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getArgs()
        viewModel = MainActivityViewModel(requireActivity().application)
        setObservers()
        viewModel.getMarker(markerID)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){
        binding.editButton.setOnClickListener{

            EditMarkerDialogFragment.instance(PointF(markerModel!!.xCord,markerModel!!.xCord), markerModel!!.imageId,markerID,false)
                .show(requireActivity().supportFragmentManager,EditMarkerDialogFragment.EDIT_MARKER)
        }
        binding.deleteButton.setOnClickListener{
            HelperObject.openDeleteDialog(this.requireContext(),"Delete Marker","Do yo really want to delete this marker, every detail associated will also be deleted",this,0)
        }
    }

    private fun getArgs(){
        if(arguments!=null){
            markerID = requireArguments().getInt(MARKER_ID_KEY)
        }else{
            dismiss()
        }
    }
    private fun setObservers(){
        viewModel.markerLive.observe(this){
            markerModel = it
            binding.nameText.text = it.person
            binding.title.text = it.title
            binding.discussionText.text = it.discussion
            if(it.uri!=null&&!it.uri.toString().isEmpty()){
                binding.imageAdded.setImageBitmap(HelperObject.getBitmap(it.uri.toString()))
                binding.imageAdded.visibility = View.VISIBLE
                binding.images.visibility = View.VISIBLE

            }
        }
    }


    companion object {
        fun instance(markerID: Int): BottomSheet {
            val args = Bundle().apply {
                putInt(MARKER_ID_KEY, markerID)
            }
            return BottomSheet().apply {
                arguments = args
            }
        }
        private const val MARKER_ID_KEY = "markerID"
    }

    override fun onDeleteItem(Id : Int) {
        if(markerModel!=null){
            viewModel.deleteMarkerUsingMarkerId(markerModel!!)
        }
        dismiss()

    }
}