package com.example.mapperapp.dialogs

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
import com.example.mapperapp.HelperObject
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
    private lateinit var launcher: ActivityResultLauncher<Intent>

    //    private var drawingId by Delegates.notNull<Int>()
    private var _binding: DialogFragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private var Id : Int = 0
    private var isAdd = true
    private lateinit var imagesReceived : ImageDetailsModel
    private lateinit var  viewModel : AddImageViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddImageBinding.inflate(inflater, container, false)
        viewModel = AddImageViewModel(requireActivity().application)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getArgs()
        handlerForOnResultActivity()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        if(isAdd){

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
        else {val model = if(isAdd) ImageDetailsModel(imageUri = uri!!, title = title, timeAdded = time) else imagesReceived.copy(title =  title)
            viewModel.addImage(model)
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
                openDialog()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    private fun openDialog() {
        val dialog = Dialog(this.requireContext(), R.style.Dialog_No_Border)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.camera_gallery_picker_view)
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setGravity(Gravity.CENTER_VERTICAL)
        dialog.setCanceledOnTouchOutside(true)
        val gallery = dialog.findViewById<TextView>(R.id.gallery)
        val camera = dialog.findViewById<TextView>(R.id.camera)
        gallery.setOnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            val mimeTypes =
                arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            galleryResultLauncher.launch(intent)
            dialog.dismiss()
        }
        camera.setOnClickListener { v: View? ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResultLauncher.launch(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun handlerForOnResultActivity() {
        galleryResultLauncher = registerForActivityResult<Intent, ActivityResult>(
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
                            saveImageToInternalStorage(selectedImageBitmap)
                        binding.attachedImage.visibility= View.VISIBLE
//                        binding.attachedImage.load(uri){
//                            error(R.drawable.delete)
//                            transformations(RoundedCornersTransformation(4f))
//                        }
                        binding.attachedImage.setImageBitmap(HelperObject.getBitmap(uri.toString()))
                        //handle
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
        cameraResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data!!
                val thumbnail =
                    data.extras!!["data"] as Bitmap? // Bitmap from camera
                uri = saveImageToInternalStorage(thumbnail!!)
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

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri? {
        val wrapper = ContextWrapper(requireActivity().application)
        var file = wrapper.getDir("IMAGE_DIRECTORY", Context.MODE_PRIVATE)
        Log.e("FILE NAME", file.absolutePath.toString())
        file = File(file, UUID.randomUUID().toString() + ".jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
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
