package com.example.mapperapp

import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapperapp.databinding.DialogFragmentEditMarkerBinding
import com.example.mapperapp.models.MarkerModel
import kotlin.properties.Delegates


class EditMarkerDialogFragment : DialogFragment() {

    private var dialogResponse: DailogResponse? = null
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
//

    companion object {
        const val X_COORDINATE_KEY = "xCoor"
        const val Y_COORDINATE_KEY = "yCoor"
        const val DRAWING_ID_KEY = "drawingID"

        fun instance(sourceCoordinates: PointF, drawingId: Int,dialogResponse: DailogResponse): EditMarkerDialogFragment {
            val args = Bundle().apply {
                putFloat(X_COORDINATE_KEY, sourceCoordinates.x)
                putFloat(Y_COORDINATE_KEY, sourceCoordinates.y)
//                putInt(DRAWING_ID_KEY, drawingId)
            }

            return EditMarkerDialogFragment().apply {
                arguments = args
                setDialogResponse(dialogResponse)
            }

        }

        const val TAG = "AddMarkerDialogFragment"
    }
    fun setDialogResponse(dialogResponse: DailogResponse){
        this.dialogResponse = dialogResponse
    }
}