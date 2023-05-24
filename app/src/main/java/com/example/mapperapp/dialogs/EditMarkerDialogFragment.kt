package com.example.mapperapp.dialogs

import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.databinding.DialogFragmentEditMarkerBinding
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.viewmodel.MainActivityViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel


class EditMarkerDialogFragment : DialogFragment() {

    private lateinit var newImageUri: Uri
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
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        setListeners()

    }

    private fun setListeners(){
        binding.done.setOnClickListener{
            addMarker()

        }
        binding.cancel.setOnClickListener{
            dismiss()
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
            viewModel.addMarker(MarkerModel(
                imageId = imageId,
                xCord = coordinatePoints.x,
                yCord = coordinatePoints.y,
                person = name,
                title = title,
                discussion = discussion))
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

        }
    }







    companion object {
        const val X_COORDINATE_KEY = "xCoor"
        const val Y_COORDINATE_KEY = "yCoor"
        const val IMAGE_ID_KEY = "imageID"

        fun instance(sourceCoordinates: PointF, imageId: Int): EditMarkerDialogFragment {
            val args = Bundle().apply {
                putFloat(X_COORDINATE_KEY, sourceCoordinates.x)
                putFloat(Y_COORDINATE_KEY, sourceCoordinates.y)
                putInt(IMAGE_ID_KEY,imageId)
            }

            return EditMarkerDialogFragment().apply {
                arguments = args
            }

        }
    }
}