package com.example.mapperapp.dialogs

import android.graphics.PointF
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
//        dialogResponse?.getResponse(true, MarkerModel(coordinatePoints.x,coordinatePoints.y,binding.title.text.toString(),binding.name.text.toString(),binding.discussion.text.toString(),ArrayList<String>()))
//        setupUIStateLiveData()
//        setupRecyclerView()
//        setupListeners()
    }

//    private fun setupUIStateLiveData() {
//        // Close dialog only after finish task
//        drawingViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
//            when (uiState) {
//                is UIState.COMPLETED -> {
//                    context?.shortToast(uiState.message)
//                    dismiss()
//                }
//                is UIState.ERROR -> {
//                    context?.longToast(uiState.message)
//                    dismiss()
//                }
//                else -> {
//                    // do nothing
//                }
//            }
//        }
//    }

    // Gets arguments from intent.
    private fun getArgs() {
        if (arguments != null) {
            coordinatePoints = PointF(
                requireArguments().getFloat(X_COORDINATE_KEY),
                requireArguments().getFloat(Y_COORDINATE_KEY)
            )
//            drawingId = requireArguments().getInt(DRAWING_ID_KEY)
        }
    }

    // Sets up images recyclerview.
//    private fun setupRecyclerView() {
//        val adapter = ImagesAdapter()
//        binding.rvImages.apply {
//            this.adapter = adapter
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            itemAnimator = DefaultItemAnimator()
//        }
//        // live data list of temporary images.
//        drawingViewModel.listOfImages.observe(viewLifecycleOwner) { list ->
//            val nullOrEmpty = list.isNullOrEmpty()
//            binding.rvImages.isVisible = !nullOrEmpty
//            if (nullOrEmpty) {
//                adapter.submitList(emptyList())
//            } else {
//                adapter.submitList(list)
//            }
//        }
//    }

    // Click Listeners
//    private fun setupListeners() {
//        binding.btnConfirm.setOnClickListener { confirmMarker() }
//        binding.btnCancel.setOnClickListener { dismiss() }
//        binding.btnAttachImages.setOnClickListener { getImagesFromGallery() }
//    }

    // Start Image Picker.
//    private fun getImagesFromGallery() {
//        ImagePicker.with(this)
//            .crop()
//            .start()
//    }

    // Confirm Marker addition. Increments in all tables.
//    private fun confirmMarker() {
//        when {
//            binding.etMarkerTitle.text.isNullOrBlank() -> {
//                context?.shortToast("Enter Title!")
//            }
//            binding.etMarkerDescription.text.isNullOrBlank() -> {
//                context?.shortToast("Enter description!")
//            }
//            binding.etAssigneeName.text.isNullOrBlank() -> {
//                context?.shortToast("Enter Assignee!")
//            }
//            else -> {
//                drawingViewModel.insertMarker(
//                    coordinatePoints.x,
//                    coordinatePoints.y,
//                    binding.etMarkerTitle.text.toString(),
//                    binding.etAssigneeName.text.toString(),
//                    binding.etMarkerDescription.text.toString(),
//                    drawingId,
//                    binding.etRemarks.text.toString()
//                )
//            }
//        }
//    }

    // ImagePicker intent result.
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (resultCode) {
//            Activity.RESULT_OK -> {
//                // Gets image file
//                val file = ImagePicker.getFile(data)!!
//                Log.d(TAG, "onActivityResult: $file")
//                // inserts original uri to temporary list.
//                drawingViewModel.insertMarkerImage(file.toUri())
//            }
//            ImagePicker.RESULT_ERROR -> {
//                Log.d(AddOrEditDrawingDialog.TAG, "onActivityResult: ${ImagePicker.getError(data)}")
//                context?.longToast("Error getting image.")
//            }
//            else -> {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }
//    }

    companion object {
        const val X_COORDINATE_KEY = "xCoor"
        const val Y_COORDINATE_KEY = "yCoor"
        const val DRAWING_ID_KEY = "drawingID"

        fun instance(sourceCoordinates: PointF, drawingId: Int,dialogResponse: DialogResponse): EditMarkerDialogFragment {
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
    fun setDialogResponse(dialogResponse: DialogResponse){
        this.dialogResponse = dialogResponse
    }
}