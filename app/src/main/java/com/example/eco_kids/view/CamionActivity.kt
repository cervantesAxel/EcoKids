package com.example.eco_kids.view

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import com.example.eco_kids.R

class CamionActivity : AppCompatActivity() {

    lateinit var camion: ImageView
    lateinit var basura: ImageView
    lateinit var basura2: ImageView
    lateinit var lata: ImageView
    lateinit var manzana: ImageView
    lateinit var puntosTxt: TextView
    lateinit var handler: Handler

    var puntos = 0
    var vidas = 3
    var juegoActivo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camion)

        camion = findViewById(R.id.camion)
        basura = findViewById(R.id.basura)
        basura2 = findViewById(R.id.basura2)
        puntosTxt = findViewById(R.id.puntos)
        lata = findViewById(R.id.lata)
        manzana = findViewById(R.id.manzana)

        handler = Handler(mainLooper)

        puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

        camion.post {
            camion.y = (resources.displayMetrics.heightPixels - camion.height - 50).toFloat()
        }

        moverBasura()
    }

    fun moverBasura() {
        handler.postDelayed(object : Runnable {
            override fun run() {

                if (!juegoActivo) return
                lata.y += 18
                manzana.y += 22
                basura.y += 20
                basura2.y += 25

                val pantallaAlto = resources.displayMetrics.heightPixels

                if (basura.y > pantallaAlto) {
                    basura.y = 0f
                    basura.x = Random.nextInt(0, 800).toFloat()
                }

                if (basura2.y > pantallaAlto) {
                    basura2.y = 0f
                    basura2.x = Random.nextInt(0, 800).toFloat()
                }
                if (lata.y > pantallaAlto) {
                    lata.y = 0f
                    lata.x = Random.nextInt(0, 800).toFloat()
                }

                if (manzana.y > pantallaAlto) {
                    manzana.y = 0f
                    manzana.x = Random.nextInt(0, 800).toFloat()
                }

                detectarColision()
                detectarColision2()
                detectarLata()
                detectarManzana()

                handler.postDelayed(this, 30)
            }
        }, 1000)
    }

    fun detectarColision() {
        if (!juegoActivo) return

        if (camion.x < basura.x + basura.width &&
            camion.x + camion.width > basura.x &&
            camion.y < basura.y + basura.height &&
            camion.y + camion.height > basura.y
        ) {
            puntos++
            puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

            basura.y = 0f
            basura.x = Random.nextInt(0, 800).toFloat()
        }
    }

    fun detectarColision2() {
        if (!juegoActivo) return

        if (camion.x < basura2.x + basura2.width &&
            camion.x + camion.width > basura2.x &&
            camion.y < basura2.y + basura2.height &&
            camion.y + camion.height > basura2.y
        ) {

            vidas--
            puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

            basura2.y = 0f
            basura2.x = Random.nextInt(0, 800).toFloat()

            if (vidas <= 0) {
                juegoActivo = false
                puntosTxt.text = "GAME OVER\nToca para reiniciar"
                handler.removeCallbacksAndMessages(null)
            }
        }
    }
    fun detectarLata() {
        if (!juegoActivo) return

        if (camion.x < lata.x + lata.width &&
            camion.x + camion.width > lata.x &&
            camion.y < lata.y + lata.height &&
            camion.y + camion.height > lata.y
        ) {
            puntos++
            puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

            lata.y = 0f
            lata.x = Random.nextInt(0, 800).toFloat()
        }
    }
    fun detectarManzana() {
        if (!juegoActivo) return

        if (camion.x < manzana.x + manzana.width &&
            camion.x + camion.width > manzana.x &&
            camion.y < manzana.y + manzana.height &&
            camion.y + camion.height > manzana.y
        ) {
            vidas--

            puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

            manzana.y = 0f
            manzana.x = Random.nextInt(0, 800).toFloat()

            if (vidas <= 0) {
                juegoActivo = false
                puntosTxt.text = "GAME OVER\nToca para reiniciar"
                handler.removeCallbacksAndMessages(null)
            }
        }
    }

    fun reiniciarJuego() {
        puntos = 0
        vidas = 3
        juegoActivo = true

        puntosTxt.text = "Puntos: $puntos  Vidas: $vidas"

        basura.y = 0f
        basura2.y = 0f
        lata.y = 0f
        manzana.y = 0f

        moverBasura()
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