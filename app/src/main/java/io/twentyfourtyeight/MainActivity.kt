package io.twentyfourtyeight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import io.twentyfourtyeight.core.Game
import io.twentyfourtyeight.core.Tile
import androidx.core.view.GestureDetectorCompat
import com.example.twentyfourtyeight.R
import io.twentyfourtyeight.core.GameListener
import io.twentyfourtyeight.core.Position


class MainActivity : AppCompatActivity(), GameListener {
    private lateinit var playAgainButton: Button
    private lateinit var message: TextView
    private lateinit var gameOverDialogue: LinearLayout
    private lateinit var gestureDetectorCompat: GestureDetectorCompat
    private lateinit var game: Game


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameOverDialogue = findViewById(R.id.gameOver)
        message = findViewById(R.id.message)
        playAgainButton = findViewById(R.id.playAgain)
        resetGame()
        val touchListener = TouchListener(this)
        gestureDetectorCompat = GestureDetectorCompat(this, touchListener)

        playAgainButton.setOnClickListener {
            resetGame()
            gameOverDialogue.visibility = View.GONE
        }
    }

    private fun resetGame() {
        for (x in 0..3) {
            for (y in 0..3) {
                changeTileUI(Tile(Position(x, y)), null)
            }
        }
        game = Game(this)
    }

    override fun onTilesMerged(from: Tile, to: Tile) {
        changeTileUI(from, null)
        changeTileUI(to, AnimationUtils.loadAnimation(this, R.anim.merge))
    }

    private fun changeTileUI(tile: Tile, animation: Animation?) {
        val id = resources.getIdentifier("row_${tile.getPosition().x}_${tile.getPosition().y}", "id", applicationContext.packageName)
        val textView = findViewById<TextView>(id)
        val card = findViewById<TextView>(id).parent as CardView
        textView.text = tile.getStringifiedValue()
        textView.setTextColor(resources.getColor(resources.getIdentifier("value_${tile.getStringifiedValue()}_fg", "color", packageName), null))
        card.setCardBackgroundColor(resources.getColor(resources.getIdentifier("value_${tile.getStringifiedValue()}_bg", "color", packageName), null))
        animation?.let { card.startAnimation(animation) }
    }

    override fun onTileAdded(tile: Tile) {
        changeTileUI(tile, AnimationUtils.loadAnimation(this, R.anim.spawn))
    }

    override fun onGameOver() {
        message.text = "Game Over!"
        gameOverDialogue.visibility = View.VISIBLE
    }

    override fun onWin() {
        message.text = "You Won 🎉"
        gameOverDialogue.visibility = View.VISIBLE
    }

     override fun onTileMoved(from: Tile, to: Tile) {
        changeTileUI(from, null)
        changeTileUI(to, null)
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