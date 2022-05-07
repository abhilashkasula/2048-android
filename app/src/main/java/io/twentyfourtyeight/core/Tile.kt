package io.twentyfourtyeight.core

class Tile(private val position: Position) {
    private var value: Int = 0
    private var isMerged: Boolean = false

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

    fun getStringifiedValue(): String {
        return if (hasValue(0)) "" else value.toString()
    }

    fun isMerged(): Boolean {
        return isMerged
    }

    fun getPosition(): Position {
        return Position(position.x, position.y)
    }
}
