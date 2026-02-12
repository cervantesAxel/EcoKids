package com.example.eco_kids.ui.theme

import androidx.compose.foundation.shape.GenericShape

val CurvedTopShape = GenericShape { size, _ ->
    // Configuraciones de estilo
    val topArcHeight = size.height * 0.025f //Qué tan arriba llega el domo
    val cornerRadiusBottom = 80f //Curvatura de las esquinas inferiores


    moveTo(0f, topArcHeight + 80f)

    // LINEA TOP
    cubicTo(
        0f, topArcHeight,                 //Esquina izquierda
        size.width * 0.2f, 0f,            //Subida al centro
        size.width / 2f, 0f               //Cuspide
    )
    cubicTo(
        size.width * 0.8f, 0f,            //Bajada del centro
        size.width, topArcHeight,         //Esquina derecha
        size.width, topArcHeight + 100f   //Final del domo
    )

    // 3. LADO DERECHO
    lineTo(size.width, size.height - cornerRadiusBottom)

    // 4. ESQUINA INFERIOR DERECHA
    quadraticBezierTo(
        size.width, size.height,
        size.width - cornerRadiusBottom, size.height
    )

    // 5. LÍNEA INFERIOR
    lineTo(cornerRadiusBottom, size.height)

    // 6. ESQUINA INFERIOR IZQUIERDA
    quadraticBezierTo(
        0f, size.height,
        0f, size.height - cornerRadiusBottom
    )

    // Cerrar el camino
    close()
}
