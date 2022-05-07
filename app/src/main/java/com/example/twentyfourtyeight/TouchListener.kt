package com.example.twentyfourtyeight

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import kotlin.math.abs

class TouchListener(private val activity: MainActivity) : SimpleOnGestureListener() {

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        val deltaX = e1.x - e2.x
        val deltaY = e1.y - e2.y

        val deltaXAbs = abs(deltaX)
        val deltaYAbs = abs(deltaY)

        if (deltaXAbs in MIN_SWIPE_DISTANCE_X..MAX_SWIPE_DISTANCE_X) {
            if (deltaX > 0) {
                activity.onSwipeLeft()
            } else {
                activity.onSwipeRight()
            }
        } else if (deltaYAbs in MIN_SWIPE_DISTANCE_Y..MAX_SWIPE_DISTANCE_Y) {
            if (deltaY > 0) {
                activity.onSwipeUp()
            } else {
                activity.onSwipeDown()
            }
        }
        return true
    }

    companion object {
        private const val MIN_SWIPE_DISTANCE_X = 100.0
        private const val MIN_SWIPE_DISTANCE_Y = 100.0

        private const val MAX_SWIPE_DISTANCE_X = 1000.0
        private const val MAX_SWIPE_DISTANCE_Y = 1000.0
    }
}