package com.example.eco_kids.ui.theme

import androidx.compose.foundation.shape.GenericShape

val CurvedTopShape = GenericShape { size, _ ->
    moveTo(0f, size.height)
    lineTo(0f, size.height * 0.2f)

    quadraticBezierTo(
        size.width / 2,
        0f,
        size.width,
        size.height * 0.2f
    )

    lineTo(size.width, size.height)
    close()
}
