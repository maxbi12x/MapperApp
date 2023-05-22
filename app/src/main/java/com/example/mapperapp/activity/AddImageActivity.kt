package com.example.mapperapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mapperapp.databinding.ActivityAddImageBinding

class AddImageActivity : AppCompatActivity() {
    var _binding : ActivityAddImageBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddImageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}