package com.example.mapperapp.markerMainScreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.PointF
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.mapperapp.adapters.MarkerAdapter
import com.example.mapperapp.utils.HelperObject
import com.example.mapperapp.databinding.ActivityMainBinding
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity(){
    private var markerList : List<MarkerModel>? = null
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainActivityViewModel
    private var imageId : Int = 0
    private lateinit var imageDetailsModel : ImageDetailsModel
    private lateinit var point : PointF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        viewModel = MainActivityViewModel(this.application)

        getDrawingIDFromIntent()
        setObservers()
        getImage()
        getMarkers()
        initTouchListener()


    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        val gestureDetector = GestureDetector(this, object : SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {

                point = binding.image.viewToSourceCoord(e.x,e.y)!!

                EditMarkerDialogFragment.instance(point, imageId,0,true)
                    .show(supportFragmentManager,EditMarkerDialogFragment.ADD_MARKER)

                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (binding.image.isReady&&markerList!=null) {
                    val tappedCoordinate = PointF(e.x, e.y)
                    val clickArea: Bitmap = binding.image.getPin()!!
                    val clickAreaWidth = clickArea.width
                    val clickAreaHeight = clickArea.height

                    for( it in markerList!!){
                        val categoryCoordinate: PointF = binding.image.sourceToViewCoord(it.xCord,it.yCord)!!
                        val categoryX = categoryCoordinate.x.toInt()
                        val categoryY = (categoryCoordinate.y - clickAreaHeight / 2).toInt()
                        if (tappedCoordinate.x >= categoryX - clickAreaWidth && tappedCoordinate.x <= categoryX + clickAreaWidth && tappedCoordinate.y >= categoryY - clickAreaHeight && tappedCoordinate.y <= categoryY + clickAreaHeight) {
                            BottomSheet.instance(it.markerId).show(supportFragmentManager,"tag")

                            break
                        }

                    }

                }
                return true
            }
        })
        binding.image.setOnTouchListener{_,e->
            gestureDetector.onTouchEvent(e)
        }

    }
    private fun getDrawingIDFromIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            imageId = bundle.getInt("IMAGE_ID")
        } else {
            finish()
            Toast.makeText(this,"Error Loading Data",Toast.LENGTH_SHORT).show()
        }
    }

    fun setAdapter(){
        binding.markerRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.markerRecycler.adapter = MarkerAdapter(markerList!!)
    }
    private fun setListeners(){
        binding.backButton.setOnClickListener{
            finish()
        }
        binding.markerList.setOnClickListener{
            if(binding.image.visibility == View.VISIBLE){
                binding.image.visibility = View.GONE
                binding.markerRecycler.visibility = View.VISIBLE
            }else{
                binding.image.visibility = View.VISIBLE
                binding.markerRecycler.visibility = View.GONE
            }
        }
    }
    private fun getImage() {
        viewModel.getImage(imageId)
    }
    private fun getMarkers(){
        viewModel.getMarkers(imageId)
    }
    private  fun setObservers(){
        viewModel.markerListLive.observe(this@MainActivity){
            binding.image.setPin(it)
            markerList = it
            setAdapter()
        }
        binding.image.apply {
            viewModel.imageLive.observe(this@MainActivity) {
                this.setImage(ImageSource.bitmap(HelperObject.getBitmap(it.imageUri.toString())!!))
                this.setDoubleTapZoomScale(0f)
                this.setMinimumDpi(100)
                this.setDoubleTapZoomDpi(999999)
                binding.imageName.text = it.title
                getMarkers()
            }
        }
    }

}