package com.github.gr3gdev

import com.github.gr3gdev.game.Game
import com.github.gr3gdev.game.Renderer
import com.github.gr3gdev.game.rendered.impl.Cube

fun main() {
    val cubeRenderer = Renderer()
    cubeRenderer.add(Cube())
    Game("Test", cubeRenderer).start()
}