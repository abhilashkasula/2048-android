package io.twentyfourtyeight.core

class Game(private val gameListener: GameListener) {
    private val board: Board = Board()

    init {
        gameListener.onTileAdded(board.addRandomTile())
        gameListener.onTileAdded(board.addRandomTile())
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
        if (isMoved) {
            gameListener.onTileAdded(board.addRandomTile())
        }
        if (isWon()) {
            return gameListener.onWin()
        }
        if (isGameOver()) {
            return gameListener.onGameOver()
        }
    }

    private fun isGameOver(): Boolean {
        return !board.isAnyTileAvailable()
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

                if (nextTileToMovablePosition != null && nextTileToMovablePosition.hasEqualValue(currentTile) && !nextTileToMovablePosition.isMerged()) {
                    isMoved = mergeTiles(currentTile, nextTileToMovablePosition) || isMoved
                    gameListener.onTilesMerged(currentTile, nextTileToMovablePosition)
                } else {
                    val movableTile = board.getTile(movablePosition)
                    movableTile?.let {
                        isMoved = moveTile(currentTile, it) || isMoved
                        gameListener.onTileMoved(currentTile, it)
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