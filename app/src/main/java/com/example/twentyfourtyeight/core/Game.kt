package com.example.twentyfourtyeight.core

class Game(private val onMoved: (Tile) -> Unit) {
    private var board: Board = Board()

    init {
        onMoved(addTile(Position(0, 0),4))
        onMoved(addTile(Position(1, 0),2))
        onMoved(addTile(Position(2, 0),2))
    }

    fun moveLeft() {
        val delta: Position.Delta = Position.Delta(0, -1)
        val traversals = mapOf(Pair("x", listOf(0, 1, 2, 3)), Pair("y", listOf(0, 1, 2, 3)))
        moveAndAddTile(delta, traversals)
    }

    fun moveRight() {
        val delta: Position.Delta = Position.Delta(0, 1)
        val traversals = mapOf(Pair("x", listOf(0, 1, 2, 3)), Pair("y", listOf(3, 2, 1, 0)))
        moveAndAddTile(delta, traversals)
    }

    fun moveUp() {
        val delta: Position.Delta = Position.Delta(-1, 0)
        val traversals = mapOf(Pair("x", listOf(0, 1, 2, 3)), Pair("y", listOf(0, 1, 2, 3)))
        moveAndAddTile(delta, traversals)
    }

    fun moveDown() {
        val delta: Position.Delta = Position.Delta(1, 0)
        val traversals = mapOf(Pair("x", listOf(3, 2, 1, 0)), Pair("y", listOf(0, 1, 2, 3)))
        moveAndAddTile(delta, traversals)
    }

    private fun moveAndAddTile(delta: Position.Delta, traversals: Map<String, List<Int>>) {
        board.resetMergeStatus()
        val isMoved = move(delta, traversals)
        if (isWon()) {
            // winning condition
        }
        if (isGameOver()) {
            // quit
        }
        if (isMoved) {
            onMoved(board.addRandomTile())
        }
    }

    private fun isGameOver(): Boolean {
        return !board.isAnyTileAvailable()
    }

    private fun addTile(position: Position, value: Int): Tile {
        return board.addTile(position, value)
    }

    private fun move(delta: Position.Delta, traversals: Map<String, List<Int>>): Boolean {
        var isMoved = false

        for (row in traversals["x"]!!) {
            for (col in traversals["y"]!!) {
                val currentPosition = Position(row, col)
                val movablePosition: Position = getFarthestPosition(currentPosition, delta)

                val currentTile = board.getTile(currentPosition)
                val nextTileToMovablePosition = board.getTile(movablePosition.incrementBy(delta))

                if (currentTile == null) {
                    continue
                }


                if (nextTileToMovablePosition != null && nextTileToMovablePosition.hasEqualValue(currentTile) && !nextTileToMovablePosition.isMerged) {
                    isMoved = mergeTiles(currentTile, nextTileToMovablePosition) || isMoved
                    onMoved(nextTileToMovablePosition)
                    onMoved(currentTile)
                } else {
                    val movableTile = board.getTile(movablePosition)
                    movableTile?.let {
                        isMoved = moveTile(currentTile, it) || isMoved
                        onMoved(currentTile)
                        onMoved(it)
                    }
                }
            }
        }

        return isMoved
    }

    private fun moveTile(from: Tile, to: Tile): Boolean {
        from.moveTo(to)
        return !from.hasEqualPosition(to) && !to.hasValue(0)
    }

    private fun isWon(): Boolean {
        return board.isAnyTileValueEquals(2048)
    }

    private fun mergeTiles(currentTile: Tile, nextTile: Tile): Boolean {
        nextTile.mergeFrom(currentTile)
        currentTile.resetValue()
        return true
    }

    private fun getFarthestPosition(position: Position, delta: Position.Delta): Position {
        val next = position.incrementBy(delta)

        if (board.isPositionValid(next) && board.isPositionAvailable(next)) {
            return getFarthestPosition(next, delta)
        }
        return position
    }
}