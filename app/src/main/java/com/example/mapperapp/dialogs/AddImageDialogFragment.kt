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
import java.util.*


class AddImageDialogFragment : DialogFragment() {


    //    private var drawingId by Delegates.notNull<Int>()
    private var _binding: DialogFragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private var onImageAdded : OnImageAdded? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddImageBinding.inflate(inflater, container, false)
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.done.setOnClickListener{
//            startActivity(Intent(requireContext(), MainActivity::class.java))
            val title = binding.title.text.toString()
            val time = Date().time
            val image : Uri = Uri.parse("a")
            val markerCount = 10
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




    companion object {
        fun instance(onImageAdded: OnImageAdded): AddImageDialogFragment {
            return AddImageDialogFragment().apply { setOnImageAdded(onImageAdded) }
        }
    }
    fun setOnImageAdded(onImageAdded: OnImageAdded){
        this.onImageAdded = onImageAdded
    }

}

interface OnImageAdded{
    fun getImageAdded(model: ImageDetailsModel)
}