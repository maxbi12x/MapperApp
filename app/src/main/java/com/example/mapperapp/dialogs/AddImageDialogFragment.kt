package com.example.mapperapp.dialogs

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mapperapp.R
import com.example.mapperapp.databinding.DialogFragmentAddImageBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.viewmodel.AddImageViewModel
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AddImageDialogFragment : DialogFragment() {


    private lateinit var launcher: ActivityResultLauncher<Intent>

    //    private var drawingId by Delegates.notNull<Int>()
    private var _binding: DialogFragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private var onImageAdded : OnImageAdded? = null
    private var Id : Int = 0
    private var isAdd = true
    private lateinit var  viewModel : AddImageViewModel
    private val image by lazy { viewModel.getImage(Id) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddImageBinding.inflate(inflater, container, false)
        viewModel = AddImageViewModel(requireActivity().application)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setResultListener()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        getArgs()
        if(isAdd){
            binding.dialogTitle.text = "ADD IMAGE"
        }else{
            binding.dialogTitle.text = "EDIT NAME"
        }

        binding.done.setOnClickListener{
            val title = binding.title.text.toString()
            val time = Date().time
            val image : Uri = Uri.parse("a")
            val model = ImageDetailsModel(
                imageUri = image,
                title = title,
                timeAdded = time)
            onImageAdded?.getImageAdded(model)

            dismiss()
        }
        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.attachImageButton.setOnClickListener{
            getNewImageUriFromGallery()
        }

    }
    private fun setResultListener(){
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                // Use the uri to load the image
                binding.attachedImage.visibility= View.VISIBLE
                binding.attachedImage.load(uri){
                    error(R.drawable.delete)

                    transformations(RoundedCornersTransformation(4f))
                }
            } else if (result.resultCode == ImagePicker.RESULT_ERROR) {
                // Use ImagePicker.Companion.getError(result.data) to show an error
            }
        }

    }
    private fun getNewImageUriFromGallery() {
        (ImagePicker.with(this.requireActivity())
            //...
            .provider(ImageProvider.BOTH).createIntentFromDialog { launcher.launch(it) })
    }
    private fun getArgs() {
        if (arguments != null && arguments?.containsKey(key) == true) {
            Id = requireArguments().getInt(key)
            isAdd = false
        }
    }

    companion object {
        val key = "EXTRAS_KEY"
        fun instance(onImageAdded: OnImageAdded,id : Int = 0,isAdd : Boolean): AddImageDialogFragment {
            return AddImageDialogFragment().apply {
                setOnImageAdded(onImageAdded)
                if(!isAdd){
                    arguments =  Bundle().apply {
                        putInt(key, id)
                    }
                }
            }
        }
    }
    fun setOnImageAdded(onImageAdded: OnImageAdded){
        this.onImageAdded = onImageAdded
    }

}

interface OnImageAdded{
    fun getImageAdded(model: ImageDetailsModel)
}