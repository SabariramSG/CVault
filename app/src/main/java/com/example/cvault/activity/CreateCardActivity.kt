package com.example.cvault.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cvault.bo.CardDetails
import com.example.cvault.databinding.ActivityCreateCardBinding
import com.example.cvault.viewmodel.CardDetailsViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateCardActivity : AppCompatActivity() {


    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private var currentImagePath: String? = null

    private lateinit var binding: ActivityCreateCardBinding

    private lateinit var viewModel: CardDetailsViewModel

    private val storageDestination: File by lazy {
        File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CardImages")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CardDetailsViewModel::class.java]
        binding.captureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.createCard.setOnClickListener {
            onSaveClicked()
        }
        binding.createCard.setOnEditorActionListener(TextView.OnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                onSaveClicked()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    private fun onSaveClicked() {
        val cardName = binding.cardNameEt.text.toString().trim()
        if (currentImagePath != null && cardName.isNotEmpty()) {
            val imageEntity = CardDetails(imageURL = currentImagePath!!, name = cardName)
            saveImageToPermanentLocation(imageEntity)
            saveImageToDatabase(imageEntity)
        } else {
            Toast.makeText(this, "Please capture an image and enter a caption", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveImageToDatabase(cardEntity: CardDetails) {
        // Execute database operations in a background thread
        viewModel.insertCardDetail(cardEntity).observe(this) { saved ->
            if (saved) {
                Toast.makeText(this, "Card saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
                Toast.makeText(this, "Error in saving card details", Toast.LENGTH_SHORT).show()
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        this.packageName + ".fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentImagePath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Display the captured image
            currentImagePath?.let {
                Glide.with(this).load(it).into(binding.capturedImage)
            }
        }
    }

    private fun saveImageToPermanentLocation(cardEntity: CardDetails): CardDetails {
        val sourceFile = File(cardEntity.imageURL ?: "")
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val destFile = File(storageDestination, fileName)

        // Copy the source file to the permanent storage directory
        sourceFile.copyTo(destFile)

        // return by updating the image path to the permanent storage path
        return cardEntity.copy(imageURL = destFile.absolutePath)
    }


}