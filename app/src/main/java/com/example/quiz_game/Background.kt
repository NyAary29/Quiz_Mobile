package com.example.quiz_game

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val colors = listOf(
    Color(0xFFdaeafe),
    Color(0xFFe1eefe),
    Color(0xFFe8f2fd),
    Color(0xFFeef6ff),
    Color(0xFFf7faff)
)

val gradientBrush = Brush.horizontalGradient(
    colors = colors
)