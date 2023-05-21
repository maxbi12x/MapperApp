package com.example.mapperapp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.PointF
import android.os.Bundle
import android.provider.ContactsContract.PinnedPositions.pin
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.mapperapp.databinding.ActivityMainBinding
import com.example.mapperapp.models.MarkerModel


class MainActivity : AppCompatActivity(),
    OnPinClickListener,DailogResponse {
    private var markerList : ArrayList<MarkerModel>? = null
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    var point  = PointF(936.25f ,291.75f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.image.setImage(ImageSource.resource(R.drawable._20623))
        binding.image.setOnPinClickListener(this);
        binding.image.setDoubleTapZoomScale(0f)

        markerList = ArrayList<MarkerModel>()
        initTouchListener()
        binding.button.setOnClickListener{
            if(markerList==null)
                Toast.makeText(this, "LINK", Toast.LENGTH_SHORT).show()
            binding.image.setPin(markerList)
        }
        setContentView(binding.root)
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        val gestureDetector = GestureDetector(this, object : SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {

                point = binding.image.viewToSourceCoord(e.x,e.y)!!
//                markerList?.add(MarkerModel(point.x,point.y,"","","",ArrayList<String>(1)))
                EditMarkerDialogFragment.instance(point,1,this@MainActivity).show(supportFragmentManager,"ADD MARKER")

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
                            markerList!!.remove(it)
                            binding.image.setPin(markerList)
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
    override fun onPinClick(pin: PointF) {

        Toast.makeText(this@MainActivity,"WORKING TEAS",Toast.LENGTH_LONG).show()
    }

    override fun getResponse(boolean: Boolean, markerModel: MarkerModel) {
        markerList?.add(markerModel)
        binding.image.setPin(markerList)
    }
}