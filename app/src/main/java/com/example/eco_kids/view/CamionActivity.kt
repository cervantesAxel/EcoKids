package com.example.eco_kids.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eco_kids.R
import kotlin.random.Random

class CamionActivity : AppCompatActivity() {

    private lateinit var camion: ImageView
    private lateinit var basura: ImageView
    private lateinit var basura2: ImageView
    private lateinit var lata: ImageView
    private lateinit var manzana: ImageView
    private lateinit var puntosTxt: TextView
    private lateinit var handler: Handler

    private var puntos = 0
    private var vidas = 3
    private var juegoActivo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camion)

        camion = findViewById(R.id.camion)
        basura = findViewById(R.id.basura)
        basura2 = findViewById(R.id.basura2)
        puntosTxt = findViewById(R.id.puntos)
        lata = findViewById(R.id.lata)
        manzana = findViewById(R.id.manzana)

        handler = Handler(Looper.getMainLooper())

        actualizarTexto()

        camion.post {
            camion.y = (resources.displayMetrics.heightPixels - camion.height - 50).toFloat()
        }

        moverObjetos()
    }

    private fun moverObjetos() {
        handler.postDelayed(object : Runnable {
            override fun run() {

                if (!juegoActivo) return

                moverObjeto(basura, 20)
                moverObjeto(basura2, 25)
                moverObjeto(lata, 18)
                moverObjeto(manzana, 22)

                detectarColisiones()

                handler.postDelayed(this, 30)
            }
        }, 1000)
    }

    private fun moverObjeto(obj: ImageView, velocidad: Int) {
        obj.y += velocidad
        val alto = resources.displayMetrics.heightPixels
        val ancho = resources.displayMetrics.widthPixels

        if (obj.y > alto) {
            obj.y = 0f
            obj.x = Random.nextInt(0, ancho - obj.width).toFloat()
        }
    }

    private fun detectarColisiones() {

        if (colision(camion, basura) || colision(camion, lata)) {
            puntos++
            actualizarTexto()
        }

        if (colision(camion, basura2) || colision(camion, manzana)) {
            vidas--
            actualizarTexto()

            if (vidas <= 0) {
                juegoActivo = false
                puntosTxt.text = "GAME OVER\nToca para reiniciar"
                handler.removeCallbacksAndMessages(null)
            }
        }
    }

    private fun colision(a: ImageView, b: ImageView): Boolean {
        val choque = a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y

        if (choque) {
            b.y = 0f
            b.x = Random.nextInt(0, resources.displayMetrics.widthPixels - b.width).toFloat()
        }

        return choque
    }

    private fun actualizarTexto() {
        puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"
    }

    private fun reiniciarJuego() {
        puntos = 0
        vidas = 3
        juegoActivo = true
        actualizarTexto()
        moverObjetos()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (!juegoActivo) {
            reiniciarJuego()
            return true
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            camion.x = event.x - camion.width / 2
        }

        return true
    }
}