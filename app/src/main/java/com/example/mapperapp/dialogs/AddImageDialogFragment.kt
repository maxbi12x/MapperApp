package com.example.mapperapp.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mapperapp.activity.MainActivity
import com.example.mapperapp.databinding.DialogFragmentAddImageBinding
import com.example.mapperapp.databinding.DialogFragmentEditMarkerBinding
import com.example.mapperapp.interfaces.DialogResponse
import com.example.mapperapp.models.AddImageRecycler
import com.example.mapperapp.models.MarkerModel


class AddImageDialogFragment : DialogFragment() {


    //    private var drawingId by Delegates.notNull<Int>()
    private var _binding: DialogFragmentAddImageBinding? = null
    private val binding get() = _binding!!

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
            startActivity(Intent(requireContext(), MainActivity::class.java))
            dismiss()
        }
        binding.cancel.setOnClickListener{
            dismiss()
        }

    }




    companion object {
        fun instance(): AddImageDialogFragment {
            return AddImageDialogFragment()
        }
    }

}