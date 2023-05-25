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
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.databinding.DialogFragmentEditMarkerBinding
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.utils.HelperObject
import com.example.mapperapp.viewmodel.MainActivityViewModel
import java.io.IOException


class EditMarkerDialogFragment : DialogFragment() {

    private var uri: Uri = Uri.parse("")
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraResultLauncher: ActivityResultLauncher<Intent>
    private var markerId: Int = 0
    private lateinit var newImageUri: Uri
    private var isAdded : Boolean = true
    private var imageId: Int = 0
    private lateinit var coordinatePoints: PointF
    private var _binding: DialogFragmentEditMarkerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentEditMarkerBinding.inflate(inflater, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel = MainActivityViewModel(requireActivity().application)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        handlerForOnResultActivity()
        if(!isAdded){
            setObservers()
            viewModel.getMarker(markerId)
        }
        setListeners()

    }
    private fun setObservers(){
        viewModel.markerLive.observe(this){
            binding.name.setText(it.person)
            binding.title.setText(it.title)
            binding.discussion.setText(it.discussion)
            if(it.uri!=null&&!uri.toString().isEmpty()){
                uri = it.uri
                binding.imageAdded.visibility = View.VISIBLE
                binding.imageAdded.setImageBitmap(HelperObject.getBitmap(uri!!.toString()))
            }
        }
    }

    private fun setListeners(){
        binding.done.setOnClickListener{
            addMarker()

        }
        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.attachImageButton.setOnClickListener{

            HelperObject.openDialog(this.requireContext(), galleryResultLauncher, cameraResultLauncher)
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
                            HelperObject.saveImageToInternalStorage(requireActivity().application,selectedImageBitmap)!!
                        binding.imageAdded.visibility = View.VISIBLE
                        binding.imageAdded.setImageBitmap(HelperObject.getBitmap(uri.toString()))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this.requireContext(),
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
                uri = HelperObject.saveImageToInternalStorage(requireActivity().application,thumbnail!!)!!
                binding.imageAdded.visibility = View.VISIBLE
                binding.imageAdded.setImageBitmap(HelperObject.getBitmap(uri.toString()))
            }
        }
    }
    private fun addMarker(){
        val name = binding.name.text.toString()
        val title = binding.title.text.toString()
        val discussion = binding.discussion.text.toString()
        if(name.isEmpty()||name.isBlank()){
            Toast.makeText(this.requireContext(), "Add name please", Toast.LENGTH_SHORT).show()
        }
        else if(title.isEmpty()||title.isBlank()){
            Toast.makeText(this.requireContext(), "Add title please", Toast.LENGTH_SHORT).show()
        }
        else if(discussion.isEmpty()||discussion.isBlank()){
            Toast.makeText(this.requireContext(), "Add discussion please", Toast.LENGTH_SHORT).show()
        }else{
            if(isAdded){
                viewModel.addMarker(MarkerModel(
                    imageId = imageId,
                    xCord = coordinatePoints.x,
                    yCord = coordinatePoints.y,
                    person = name,
                    title = title,
                    discussion = discussion,
                    uri = uri!!))
            }
            else{
                viewModel.updateMarker(MarkerModel(
                    markerId = markerId,
                    imageId = imageId,
                    xCord = coordinatePoints.x,
                    yCord = coordinatePoints.y,
                    person = name,
                    title = title,
                    discussion = discussion,
                    uri = uri!!))
            }

            dismiss()
        }
    }
    private fun getArgs() {
        if (arguments != null) {
            coordinatePoints = PointF(
                requireArguments().getFloat(X_COORDINATE_KEY),
                requireArguments().getFloat(Y_COORDINATE_KEY)
            )
            imageId = requireArguments().getInt(IMAGE_ID_KEY)
            isAdded = requireArguments().getBoolean(IS_ADD)
            markerId = requireArguments().getInt(MARKER_ID)

        }
    }

    companion object {
        const val X_COORDINATE_KEY = "xCoor"
        const val Y_COORDINATE_KEY = "yCoor"
        const val IMAGE_ID_KEY = "imageID"
        const val ADD_MARKER = "ADD MARKER"
        const val EDIT_MARKER = "EDIT MARKER"
        const val MARKER_ID = "MARKER_ID"
        const val IS_ADD = "IS_ADD"

        fun instance(sourceCoordinates: PointF, imageId: Int,markerId: Int,isAdd : Boolean): EditMarkerDialogFragment {
            val args = Bundle().apply {
                putFloat(X_COORDINATE_KEY, sourceCoordinates.x)
                putFloat(Y_COORDINATE_KEY, sourceCoordinates.y)
                putInt(IMAGE_ID_KEY,imageId)
                putBoolean(IS_ADD,isAdd)
                if(!isAdd){
                    putInt(MARKER_ID,markerId)
                }
            }

            return EditMarkerDialogFragment().apply {
                arguments = args
            }

        }
    }
}