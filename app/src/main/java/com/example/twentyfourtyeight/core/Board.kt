package com.example.twentyfourtyeight.core

import kotlin.random.Random

class Board {

    private val tiles: MutableList<List<Tile>> = createTiles()
    private var flattenedTiles: List<Tile> = tiles.flatten()
    private val bounds: Bounds = Bounds(0, 3, 0, 3)

    data class Bounds(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int)

    fun isPositionValid(position: Position): Boolean {
        return position.isInBounds(bounds)
    }

    fun isPositionAvailable(position: Position): Boolean {
        val tile = tiles[position.x][position.y]
        return tile.hasValue(0)
    }

    fun getTile(pos: Position): Tile? {
        if (isPositionValid(pos)) {
            return tiles[pos.x][pos.y]
        }
        return null
    }

    fun getPreviousTile(nextTile: Tile, delta: Position.Delta): Tile? {
        val previousPosition = nextTile.getPreviousPosition(delta)
        return getTile(previousPosition)
    }

    fun addTile(position: Position, value: Int): Tile {
//        val available = flattenedTiles.filter { it.hasValue(0) }
//        val tile = available[Random.nextInt(0, available.size - 1)]
        val tile = tiles[position.x][position.y]
        tile.updateValue(value)
        return tile
    }

    fun addRandomTile(): Tile {
        val available = flattenedTiles.filter { it.hasValue(0) }
        val tile = available[Random.nextInt(0, available.size - 1)]
        tile.updateValue(2)
        return tile
    }

    fun isAnyTileValueEquals(value: Int): Boolean {
        return flattenedTiles.any { it.hasValue(value) }
    }

    fun isAnyTileAvailable(): Boolean {
        val available = flattenedTiles.filter { it.hasValue(0) }
        return available.isNotEmpty()
    }

    fun resetMergeStatus() {
        flattenedTiles.forEach { it.resetMergeStatus() }
    }
}

private fun createTiles(): MutableList<List<Tile>> {
    val tiles = mutableListOf<List<Tile>>()

    for (x in 0..3) {
        val element = listOf(
            Tile(Position(x, 0)),
            Tile(Position(x, 1)),
            Tile(Position(x, 2)),
            Tile(Position(x, 3))
        )
        tiles.add(element)
    }

    return tiles
}
