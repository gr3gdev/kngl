package com.github.gr3gdev.game

import com.github.gr3gdev.game.rendered.impl.Cube
import kotlin.test.Test

class GameTest {

    @Test
    fun test() {
        val renderer = Renderer()
        renderer.add(Cube())
        val game = Game("Test", renderer)
        game.open()
        //val game = GameBis()
    }
}