package com.github.gr3gdev

import com.github.gr3gdev.game.Game
import com.github.gr3gdev.game.Renderer
import com.github.gr3gdev.game.rendered.impl.Cube
import com.github.gr3gdev.game.rendered.impl.Triangle

fun main() {
    val renderer = Renderer()
    renderer.add(Cube())
    Game("Test", renderer).open()
}