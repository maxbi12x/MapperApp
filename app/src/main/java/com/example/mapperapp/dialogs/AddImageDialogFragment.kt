package com.example.mapperapp.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.activity.MainActivity
import com.example.mapperapp.databinding.DialogFragmentAddImageBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.viewmodel.AddImageViewModel
import java.util.*


class AddImageDialogFragment : DialogFragment() {


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