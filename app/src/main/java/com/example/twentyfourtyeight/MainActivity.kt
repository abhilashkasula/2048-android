package com.example.twentyfourtyeight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.twentyfourtyeight.core.Game
import com.example.twentyfourtyeight.core.Tile
import androidx.core.view.GestureDetectorCompat


class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetectorCompat: GestureDetectorCompat
    private lateinit var game: Game


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game = Game(::onTileChangeListener)
        val touchListener = TouchListener(this)
        gestureDetectorCompat = GestureDetectorCompat(this, touchListener)
    }

    private fun onTileChangeListener(tile: Tile) {
        val id = resources.getIdentifier("row_${tile.getPosition().x}_${tile.getPosition().y}", "id", applicationContext.packageName)
        val textView = findViewById<TextView>(id)
        val card = findViewById<TextView>(id).parent as CardView
        textView.text = tile.getStringifiedValue()
        textView.setTextColor(resources.getColor(resources.getIdentifier("value_${tile.getStringifiedValue()}_fg", "color", packageName), null))
        card.setCardBackgroundColor(resources.getColor(resources.getIdentifier("value_${tile.getStringifiedValue()}_bg", "color", packageName), null))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) return true else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            game.moveDown()
            return true
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            game.moveUp()
            return true
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            game.moveLeft()
            return true
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            game.moveRight()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun onSwipeLeft() {
        game.moveLeft()
    }

    fun onSwipeUp() {
        game.moveUp()
    }

    fun onSwipeRight() {
        game.moveRight()
    }

    fun onSwipeDown() {
        game.moveDown()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetectorCompat.onTouchEvent(event)
        return true
    }
}