package ru.radmirnaumov.randombg

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Константы для запросов
    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private const val REQUEST_CODE_GALLERY = 101
        private const val REQUEST_CODE_COLOR = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Toast.makeText(this, "Удерживайте кнопку для выбора своего фона", Toast.LENGTH_LONG).show()
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // При долгом нажатии - выбор изображения, при обычном - случайный цвет
            button.setOnLongClickListener {
                checkPermissions()
                true
            }
            changeBgWithColor()
        }
    }

    private fun changeBgWithColor() {
        try {
            val colors = listOf(
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN,
                Color.MAGENTA, Color.GRAY, Color.DKGRAY, Color.LTGRAY,
                Color.WHITE, Color.BLACK, Color.parseColor("#FFA500"),
                Color.parseColor("#800080"), Color.parseColor("#008000"),
                Color.parseColor("#FF6347"), Color.parseColor("#FFD700"),
                Color.parseColor("#4B0082"), Color.parseColor("#FF69B4"),
                Color.parseColor("#00FF7F"), Color.parseColor("#7FFFD4"),
                Color.parseColor("#9932CC"), Color.parseColor("#8A2BE2"),
                Color.parseColor("#DC143C"), Color.parseColor("#00FFFF"),
                Color.parseColor("#000080"), Color.parseColor("#FF8C00"),
                Color.parseColor("#E6E6FA"), Color.parseColor("#FF00FF"),
                Color.parseColor("#1E90FF"), Color.parseColor("#ADFF2F"),
                Color.parseColor("#FF4500")
            )

            findViewById<View>(R.id.main).setBackgroundColor(colors.random())
            findViewById<Button>(R.id.button).setBackgroundColor(colors.random())
        } catch (e: Exception) {
            Log.e("Background", "Ошибка смены фона: ${e.message}")
        }
    }

    private fun checkPermissions() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_PERMISSION)
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(this, "Нужно разрешение для выбора фона", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let { uri ->
                try {
                    findViewById<View>(R.id.main).background = null
                    findViewById<View>(R.id.main).setBackgroundURI(uri)
                } catch (e: Exception) {
                    Toast.makeText(this, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Расширение для установки фона из URI
    private fun View.setBackgroundURI(uri: Uri) {
        this.background = android.graphics.drawable.BitmapDrawable(
            resources,
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        )
    }
}