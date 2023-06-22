package com.app.poojastoreowner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CategoryBottomSheetDialog(
    private var unitInterface: UnitInterface?
) : BottomSheetDialogFragment() {

    private lateinit var imageView: AppCompatImageView

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d("DATA_IMAGE", "Data ${data?.extras}")
                val photo = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(photo)

                persistImage(photo, "Sample")
            }
        }

    private fun persistImage(bitmap: Bitmap, name: String) {
        val filesDir: File = requireActivity().filesDir
        val imageFile = File(filesDir, "$name.jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            Log.d("DATA_IMAGE", "File Dir $filesDir")
            Log.d("DATA_IMAGE", "File $imageFile")

        } catch (e: Exception) {
            Log.d("DATA_IMAGE", "Exce ${e.message}")
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.e("DEBUG", "${it.key} = ${it.value}")
                if (it.key == android.Manifest.permission.CAMERA && it.value) {
                    dispatchTakePictureIntent()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_category_layout, container, false)

        val algoButton = view.findViewById<FloatingActionButton>(R.id.addUnit)
        val metrics = view.findViewById<TextInputEditText>(R.id.metricsEdittext)
        val price = view.findViewById<TextInputEditText>(R.id.priceEditText)
        imageView = view.findViewById<AppCompatImageView>(R.id.imageView)

        imageView.setOnClickListener {
            requestMultiplePermissions.launch(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

        algoButton.setOnClickListener {
            val isValid = checkRecordValidations(metrics.text.toString(), price.text.toString())
            if (isValid) {
                //show alert and return
                checkForRecordExist(
                    UnitItem(
                        1,
                        metrics.text.toString().trim(),
                        price.text.toString(),
                        true
                    )
                )
                dismiss()
            }
        }

        return view
    }

    private fun checkRecordValidations(metrics: String?, price: String?): Boolean {
        if (metrics == null || TextUtils.isEmpty(metrics) || metrics.toInt() == 0) return false
        if (price == null || TextUtils.isEmpty(metrics) || price.toDouble() == 0.0) return false
        return true
    }

    private fun checkForRecordExist(unitItem: UnitItem) {
        unitInterface?.updateUnit(unitItem)
    }

    companion object {
        fun newInstance(unitInterface: UnitInterface?): CategoryBottomSheetDialog {
            return CategoryBottomSheetDialog(unitInterface)
        }
    }

    private fun dispatchTakePictureIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }


}

