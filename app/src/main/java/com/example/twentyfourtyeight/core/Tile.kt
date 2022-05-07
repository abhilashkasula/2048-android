package com.example.twentyfourtyeight.core

class Tile(val position: Position) {
    var value: Int = 0
    var isMerged: Boolean = false

    fun hasEqualValue(currentTile: Tile): Boolean {
        return value == currentTile.value
    }

    fun updateValue(newValue: Int) {
        value = newValue
    }

    fun resetValue() {
        value = 0
    }

    fun hasValue(value: Int): Boolean {
        return this.value == value
    }

    fun getPreviousPosition(delta: Position.Delta): Position {
        return position.decrementBy(delta)
    }

    fun mergeFrom(currentTile: Tile) {
        updateValue(value + currentTile.value)
        isMerged = true
    }

    fun hasEqualPosition(other: Tile): Boolean {
        return position == other.position
    }

    fun moveTo(to: Tile) {
        val temp = value
        value = 0
        to.updateValue(temp)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Tile) return false
        return value == other.value && position == other.position
    }

    fun resetMergeStatus() {
        isMerged = false
    }
}
