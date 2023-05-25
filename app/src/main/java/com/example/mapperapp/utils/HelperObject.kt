package com.example.mapperapp.utils

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.format.DateUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.example.mapperapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

object HelperObject {
    fun getRelativeTime(date: Date): String {
        val currentTime = Date()
        val relativeTime = DateUtils.getRelativeTimeSpanString(date.time, currentTime.time, DateUtils.MINUTE_IN_MILLIS)
        return relativeTime.toString()
    }
    fun getBitmap(uri : String) : Bitmap?{
        val imgFile = File(uri)
        var r : Bitmap? = null
        if (imgFile.exists()) {
            r = BitmapFactory.decodeFile(imgFile.absolutePath)

        }
        return r
    }

    fun openDialog(context: Context, galleryResultLauncher: ActivityResultLauncher<Intent>, cameraResultLauncher: ActivityResultLauncher<Intent>) {
        val dialog = Dialog(context, R.style.Dialog_No_Border)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.camera_gallery_picker_view)
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setGravity(Gravity.CENTER_VERTICAL)
        dialog.setCanceledOnTouchOutside(true)
        val gallery = dialog.findViewById<TextView>(R.id.gallery)
        val camera = dialog.findViewById<TextView>(R.id.camera)
        gallery.setOnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            val mimeTypes =
                arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            galleryResultLauncher.launch(intent)
            dialog.dismiss()
        }
        camera.setOnClickListener { v: View? ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResultLauncher.launch(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
    fun saveImageToInternalStorage(application: Application,bitmap: Bitmap): Uri? {
        val wrapper = ContextWrapper(application)
        var file = wrapper.getDir("IMAGE_DIRECTORY", Context.MODE_PRIVATE)
        Log.e("FILE NAME", file.absolutePath.toString())
        file = File(file, UUID.randomUUID().toString() + ".jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    fun openDeleteDialog(context: Context,title : String, message : String,deleteItem: DeleteItem,Id : Int) {
        val dialog = Dialog(context, R.style.Dialog_No_Border)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.ask_delete_dialog)
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setGravity(Gravity.CENTER_VERTICAL)
        dialog.setCanceledOnTouchOutside(true)
        val titleTx = dialog.findViewById<TextView>(R.id.title)
        val messageTx = dialog.findViewById<TextView>(R.id.message)
        val ok = dialog.findViewById<TextView>(R.id.ok)
        val cancel = dialog.findViewById<TextView>(R.id.cancel)
        titleTx.text = title
        messageTx.text = message
        ok.setOnClickListener { v: View? ->
            deleteItem.onDeleteItem(Id)
            dialog.dismiss()
        }
        cancel.setOnClickListener { v: View? ->
            dialog.dismiss()
        }
        dialog.show()
    }
    interface DeleteItem{
        fun onDeleteItem(Id : Int)
    }
}