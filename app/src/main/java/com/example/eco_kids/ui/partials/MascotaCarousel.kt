package com.example.eco_kids.ui.partials

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eco_kids.viewmodel.WelcomeViewModel
import kotlin.math.absoluteValue

@Composable
fun MascotaCarousel(viewModel: WelcomeViewModel) {

    val realSize = viewModel.imagenes.size

    //Se empieza la lista en medio para poder ir a ambos lados
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    LaunchedEffect(pagerState.currentPage) {
        val realIndex =
            pagerState.currentPage % viewModel.imagenes.size

        viewModel.onMascotaSeleccionadaChange(realIndex)
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 100.dp),
        pageSpacing = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->

        //Se convierte la página infinita en índice real
        val realIndex = page % realSize

        val pageOffset = (
                (pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction
                ).absoluteValue

        val scale = 0.7f + (1 - pageOffset.coerceIn(0f, 1f)) * 0.4f
        val alpha = 0.5f + (1 - pageOffset.coerceIn(0f, 1f)) * 0.5f

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
                .width(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = viewModel.imagenes[realIndex]),
                contentDescription = "Mascota ${realIndex + 1}",
                modifier = Modifier.size(220.dp)
            )
        }
    }
}

