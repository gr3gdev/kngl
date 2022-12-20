package com.github.gr3gdev.game

import com.github.gr3gdev.game.rendered.impl.Triangle
import kotlin.test.Test

class GameTest {

    @Test
    fun test() {
        val renderer = Renderer()
        renderer.add(Triangle())
        val game = Game("Test", renderer)
        game.open()
        //val game = GameBis()
    }
}