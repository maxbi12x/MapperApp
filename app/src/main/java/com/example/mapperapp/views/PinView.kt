package com.example.mapperapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.mapperapp.interfaces.OnPinClickListener
import com.example.mapperapp.R
import com.example.mapperapp.models.MarkerModel

class PinView @JvmOverloads constructor(context: Context?, attr: AttributeSet? = null) :
    SubsamplingScaleImageView(context, attr) {
    private val paint = Paint()
    private val vPin = PointF()
    private var markerList: ArrayList<MarkerModel>? = null


    init {
        initialise()
    }

    private var pin: Bitmap? = null

    fun getPin(): Bitmap? {
        return pin
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun setPin(markerList: ArrayList<MarkerModel>?) {
        this.markerList = markerList
        initialise()
        invalidate()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initialise() {
        val density = resources.displayMetrics.densityDpi.toFloat()
        pin = drawableToBitmap(this.resources.getDrawable(R.drawable.pushpin_blue))
        val w = density / 420f * pin!!.width
        val h = density / 420f * pin!!.height
        pin = Bitmap.createScaledBitmap(pin!!, w.toInt(), h.toInt(), true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isReady||pin==null) {
            return
        }
        paint.isAntiAlias = true
        markerList?.forEach{
            sourceToViewCoord(it.xCord,it.yCord, vPin)
            val vX = vPin.x - pin!!.width / 2
            val vY = vPin.y - pin!!.height
            Log.e("VALUE", "$vX $vY")
            canvas.drawBitmap(pin!!, vX, vY, paint)
        }
    }

    private var onPinClickListener: OnPinClickListener? = null
    fun setOnPinClickListener(listener: OnPinClickListener?) {
        onPinClickListener = listener
    }
}