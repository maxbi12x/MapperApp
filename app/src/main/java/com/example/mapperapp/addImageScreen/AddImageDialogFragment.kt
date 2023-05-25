package com.example.mapperapp.addImageScreen

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.utils.HelperObject
import com.example.mapperapp.R
import com.example.mapperapp.databinding.DialogFragmentAddImageBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.viewmodel.AddImageViewModel
import java.io.*
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")
class AddImageDialogFragment : DialogFragment() {


    private lateinit var cameraResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private var uri: Uri?=null
    private var _binding: DialogFragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private var Id : Int = 0
    private var isAdd = true
    private lateinit var imagesReceived : ImageDetailsModel
    private lateinit var  viewModel : AddImageViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogFragmentAddImageBinding.inflate(inflater, container, false)
        viewModel = AddImageViewModel(requireActivity().application)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getArgs()
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        if(isAdd){
            handlerForOnResultActivity()
            binding.dialogTitle.text = "ADD IMAGE"
        }else{
            getImageDetails()
            binding.dialogTitle.text = "EDIT NAME"
        }

        binding.done.setOnClickListener{
            saveImage()
        }
        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.attachImageButton.setOnClickListener{
            openGallery()
//            openGallery() -> openDialog() -> handler() -> saveToInternalStrorage
        }

    }
    private fun saveImage(){
        val title = binding.title.text.toString()
        val time = Date().time
        if(title.isEmpty() || title.isEmpty()){
            Toast.makeText(this.requireContext(), "Please enter the title", Toast.LENGTH_SHORT).show()
        }
        else if(uri==null){
            Toast.makeText(this.requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
        }
        else {
            if(isAdd)
                viewModel.addImage(ImageDetailsModel(imageUri = uri!!, title = title, timeAdded = time))
            else
                viewModel.updateImageName(imagesReceived.copy(title =  title))
            dismiss()
        }
    }
    private fun openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf<String>(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    200
                )
            } else {
                HelperObject.openDialog(this.requireContext(),galleryResultLauncher,cameraResultLauncher)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    private fun handlerForOnResultActivity() {
        galleryResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, contentURI)
                        uri =
                            HelperObject.saveImageToInternalStorage(requireActivity().application,selectedImageBitmap)
                        binding.attachedImage.visibility= View.VISIBLE
                        binding.attachedImage.setImageBitmap(HelperObject.getBitmap(uri.toString()))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@AddImageDialogFragment.requireContext(),
                            "Failed To Save Image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        cameraResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data!!
                val thumbnail =
                    data.extras!!["data"] as Bitmap? // Bitmap from camera
                uri = HelperObject.saveImageToInternalStorage(requireActivity().application,thumbnail!!)
                binding.attachedImage.visibility= View.VISIBLE
                binding.attachedImage.setImageBitmap(HelperObject.getBitmap(uri.toString()))
            }
        }
    }
    private fun getImageDetails() {
        viewModel.getImage(Id)
        viewModel.imageLive.observe(this){
            imagesReceived = it
            binding.title.setText(it.title)
            binding.attachedImage.visibility= View.VISIBLE
            uri = it.imageUri
            binding.attachedImage.setImageBitmap(HelperObject.getBitmap(uri.toString()))
            binding.attachImageButton.visibility = View.GONE
            binding.attachedImage
        }
    }
    private fun getArgs() {
        if (arguments != null && arguments?.containsKey(key) == true) {
            Id = requireArguments().getInt(key)
            isAdd = false
        }
    }
    companion object {
        val key = "EXTRAS_KEY"
        fun instance(id : Int = 0,isAdd : Boolean): AddImageDialogFragment {
            return AddImageDialogFragment().apply {
                if(!isAdd){
                    arguments =  Bundle().apply {
                        putInt(key, id)
                    }
                }
            }
        }
    }

}
