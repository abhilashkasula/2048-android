package com.example.twentyfourtyeight.core

interface GameListener {
    fun onTileMoved(from: Tile, to: Tile)
    fun onTilesMerged(from: Tile, to: Tile)
    fun onTileAdded(tile: Tile)
    fun onGameOver()
    fun onWin()
}