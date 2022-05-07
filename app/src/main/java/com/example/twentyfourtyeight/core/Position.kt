package com.example.twentyfourtyeight.core

class Position(var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Position) return false
        return x == other.x && y == other.y
    }

    fun decrementBy(delta: Delta): Position {
        return Position(x - delta.x, y - delta.y)
    }

    fun isInBounds(bounds: Board.Bounds): Boolean {
        return x >= bounds.xMin && x <= bounds.xMax && y >= bounds.yMin && y <= bounds.yMax
    }

    fun incrementBy(delta: Delta): Position {
        return Position(x + delta.x, y + delta.y)
    }

    data class Delta(val x: Int, val y: Int)
}