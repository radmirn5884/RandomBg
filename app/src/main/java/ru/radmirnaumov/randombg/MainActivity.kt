package ru.radmirnaumov.randombg

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            changebg()
        }
    }

    private fun changebg() {
        try {
            val colors = listOf(
                // Стандартные цвета Android
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
                Color.GRAY,
                Color.DKGRAY,
                Color.LTGRAY,
                Color.WHITE,
                Color.BLACK,

                // Пользовательские цвета в HEX
                Color.parseColor("#FFA500"),    // Оранжевый
                Color.parseColor("#800080"),    // Фиолетовый
                Color.parseColor("#008000"),    // Темно-зеленый
                Color.parseColor("#FF6347"),    // Томатный
                Color.parseColor("#FFD700"),    // Золотой
                Color.parseColor("#4B0082"),    // Индиго
                Color.parseColor("#FF69B4"),    // Ярко-розовый
                Color.parseColor("#00FF7F"),    // Весенний зеленый
                Color.parseColor("#7FFFD4"),    // Аквамарин
                Color.parseColor("#9932CC"),    // Темно-орхидея
                Color.parseColor("#8A2BE2"),    // Сине-фиолетовый
                Color.parseColor("#DC143C"),    // Малиновый
                Color.parseColor("#00FFFF"),    // Аква (бирюзовый)
                Color.parseColor("#000080"),    // Темно-синий (флотский)
                Color.parseColor("#FF8C00"),    // Темно-оранжевый
                Color.parseColor("#E6E6FA"),    // Лавандовый
                Color.parseColor("#FF00FF"),    // Фуксия
                Color.parseColor("#1E90FF"),    // Ярко-синий
                Color.parseColor("#ADFF2F"),    // Зелено-желтый
                Color.parseColor("#FF4500")      // Красно-оранжевый
            )

            // Меняем цвет фона активности
            findViewById<View>(R.id.main).setBackgroundColor(colors.random())

            // Меняем цвет фона кнопки
            findViewById<Button>(R.id.button).setBackgroundColor(colors.random())

        } catch (e: Exception) {
            Log.e("Background", "Ошибка смены фона: ${e.message}")
        }
    }
}
