package com.example.eco_kids.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco_kids.R
import com.example.eco_kids.model.MemoryCard
import com.example.eco_kids.model.TrashType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _isMatch = mutableStateOf(false)
    val isMatch: State<Boolean> = _isMatch
    val cards = mutableStateListOf<MemoryCard>()

    var score by mutableIntStateOf(0)
    var showVictoryDialog by mutableStateOf(false)

    // Contadores
    var plasticCount by mutableIntStateOf(0)
    var paperCount by mutableIntStateOf(0)
    var organicCount by mutableIntStateOf(0)

    // Bloqueo para evitar errores de clic rápido
    private var isProcessing = false

    private var firstSelectedCardIndex: Int? = null
    private var matchesFound = 0
    private var totalPairs = 0

    init {
        setupGame()
    }

    fun resetGameForNavigation() {
        showVictoryDialog = false
        isProcessing = false
        cards.clear()
    }

    fun setupGame() {
        score = 0
        matchesFound = 0
        plasticCount = 0; paperCount = 0; organicCount = 0
        showVictoryDialog = false
        _isMatch.value = false
        firstSelectedCardIndex = null
        isProcessing = false
        cards.clear()

        // DATOS: Asegúrate que coincidan con tus imágenes en res/drawable
        val rawData = listOf(
            Pair(R.drawable.pet_yogurt, TrashType.PLASTIC),
            Pair(R.drawable.pet_tapas, TrashType.PLASTIC),
            Pair(R.drawable.pet_botella, TrashType.PLASTIC),
            Pair(R.drawable.pet_vaso, TrashType.PLASTIC),
            Pair(R.drawable.pet_cuchara, TrashType.PLASTIC),
            Pair(R.drawable.pet_vaso, TrashType.PLASTIC),

            Pair(R.drawable.pap_caja, TrashType.PAPER),
            Pair(R.drawable.pap_cajas, TrashType.PAPER),
            Pair(R.drawable.pap_cafe, TrashType.PAPER),
            Pair(R.drawable.pap_libros, TrashType.PAPER),
            Pair(R.drawable.pap_cereal, TrashType.PAPER),
            Pair(R.drawable.pap_rollo, TrashType.PAPER),
            Pair(R.drawable.pap_sobre, TrashType.PAPER),
            Pair(R.drawable.pap_periodico, TrashType.PAPER),

            Pair(R.drawable.org_papa, TrashType.ORGANIC),
            Pair(R.drawable.org_lechuga, TrashType.ORGANIC),
            Pair(R.drawable.org_manzana, TrashType.ORGANIC),
            Pair(R.drawable.org_hoja, TrashType.ORGANIC),
            Pair(R.drawable.org_flor, TrashType.ORGANIC),
            Pair(R.drawable.org_aguacate, TrashType.ORGANIC),
            Pair(R.drawable.org_banano, TrashType.ORGANIC),
            Pair(R.drawable.org_te, TrashType.ORGANIC),



            )
        totalPairs = rawData.size

        val numberOfPairs = 6

        val selectedData = rawData.shuffled().take(numberOfPairs)
        totalPairs = selectedData.size

        val deck = (selectedData + selectedData).shuffled()

        deck.forEachIndexed { index, item ->
            cards.add(MemoryCard
                (id = index,
                imageRes = item.first,
                type = item.second))
        }
    }

    fun onCardClicked(index: Int) {
        if (isProcessing || cards[index].isFaceUp || cards[index].isMatched || cards[index].isStored) return

        cards[index] = cards[index].copy(isFaceUp = true)

        if (firstSelectedCardIndex == null) {
            firstSelectedCardIndex = index
        } else {
            checkForMatch(firstSelectedCardIndex!!, index)
        }
    }

    fun resetMatch() {
        _isMatch.value = false
    }
    private fun checkForMatch(index1: Int, index2: Int) {
        isProcessing = true // Bloqueamos

        if (cards[index1].imageRes == cards[index2].imageRes) {
            // --- ACIERTO ---
            _isMatch.value = true
            viewModelScope.launch {
                delay(600)
                cards[index1] = cards[index1].copy(isMatched = true)
                cards[index2] = cards[index2].copy(isMatched = true)

                score += 100 // Sumar puntos
                matchesFound++

                delay(500)
                cards[index1] = cards[index1].copy(isStored = true)
                cards[index2] = cards[index2].copy(isStored = true)

                when (cards[index1].type) {
                    TrashType.PLASTIC -> plasticCount++
                    TrashType.PAPER -> paperCount++
                    TrashType.ORGANIC -> organicCount++
                }

                firstSelectedCardIndex = null
                isProcessing = false

                if (matchesFound == totalPairs) {
                    delay(500)
                    showVictoryDialog = true
                }
            }
        } else {
            _isMatch.value = false
            // --- ERROR (NO COINCIDEN) ---
            viewModelScope.launch {
                delay(1000)
                cards[index1] = cards[index1].copy(isFaceUp = false)
                cards[index2] = cards[index2].copy(isFaceUp = false)

                // NUEVO: Restar 10 puntos, pero sin bajar de 0
                score = (score - 10).coerceAtLeast(0)

                firstSelectedCardIndex = null
                isProcessing = false
            }
        }
    }
}