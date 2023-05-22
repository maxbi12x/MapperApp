package com.example.mapperapp.dialogs

import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.databinding.DialogFragmentEditMarkerBinding
import com.example.mapperapp.interfaces.DialogResponse
import com.example.mapperapp.models.MarkerModel


class EditMarkerDialogFragment : DialogFragment() {

    private var dialogResponse: DialogResponse? = null
    private lateinit var coordinatePoints: PointF
//    private var drawingId by Delegates.notNull<Int>()
    private var _binding: DialogFragmentEditMarkerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentEditMarkerBinding.inflate(inflater, container, false)
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
        getArgs()
        binding.done.setOnClickListener{
            dialogResponse?.getResponse(true, MarkerModel(coordinatePoints.x,coordinatePoints.y,binding.title.text.toString(),binding.name.text.toString(),binding.discussion.text.toString(),ArrayList<String>()))
            dismiss()
        }
        binding.cancel.setOnClickListener{
            dismiss()
        }
    }


    private fun getArgs() {
        if (arguments != null) {
            coordinatePoints = PointF(
                requireArguments().getFloat(X_COORDINATE_KEY),
                requireArguments().getFloat(Y_COORDINATE_KEY)
            )

        }
    }







    companion object {
        const val X_COORDINATE_KEY = "xCoor"
        const val Y_COORDINATE_KEY = "yCoor"
        const val DRAWING_ID_KEY = "drawingID"

        fun instance(sourceCoordinates: PointF, drawingId: Int,dialogResponse: DialogResponse): EditMarkerDialogFragment {
            val args = Bundle().apply {
                putFloat(X_COORDINATE_KEY, sourceCoordinates.x)
                putFloat(Y_COORDINATE_KEY, sourceCoordinates.y)
            }

            return EditMarkerDialogFragment().apply {
                arguments = args
                setDialogResponse(dialogResponse)
            }

        }
    }
    fun setDialogResponse(dialogResponse: DialogResponse){
        this.dialogResponse = dialogResponse
    }
}