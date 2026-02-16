package com.example.eco_kids.ui.partials

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eco_kids.viewmodel.WelcomeViewModel
import kotlin.math.abs

@Composable
fun MascotaCarousel(viewModel: WelcomeViewModel) {
    val pagerState = rememberPagerState(pageCount = { viewModel.imagenes.size })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 72.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        // Calculamos cuánto se ha desplazado la página actual del centro
        val pageOffset = (
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                )

        // Aplicamos la escala: 1. 0f en el centro, baja hasta 0.7f en los extremos
        val scale = 1f - (abs(pageOffset) * 0.3f).coerceIn(0f, 0.3f)

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    // Opcional: añadir transparencia a los que no están enfocados
                    alpha = 0.5f + (scale - 0.7f) / 0.3f * 0.5f
                }
                .fillMaxWidth(0.7f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = viewModel.imagenes[page]),
                contentDescription = "Mascota ${page + 1}",
                modifier = Modifier.size(200.dp) // Ajusta según tu diseño
            )
        }
    }
}