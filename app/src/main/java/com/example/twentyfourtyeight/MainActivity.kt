package com.example.twentyfourtyeight

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.twentyfourtyeight.core.Game
import com.example.twentyfourtyeight.core.Tile
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetectorCompat: GestureDetectorCompat
    private lateinit var game: Game


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game = Game(::onTileMoved)
        val touchListener = TouchListener(this)
        gestureDetectorCompat = GestureDetectorCompat(this, touchListener)
    }

    private fun onTileMoved(tile: Tile) {
        val id = resources.getIdentifier("row_${tile.position.x}_${tile.position.y}", "id", applicationContext.packageName)
        val view = findViewById<TextView>(id)
        view.text = if (tile.value == 0) "" else tile.value.toString()
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