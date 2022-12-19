package com.github.gr3gdev.game

import com.github.gr3gdev.game.rendered.impl.Triangle
import kotlin.test.Test

class GameTest {

    @Test
    fun test() {
        val renderer = Renderer()
        renderer.add(Triangle())
        val game = Game(
            "Test", renderer,
            """#version 410 core
layout(location = 0) in vec3 vertexPosition_modelspace;
void main() {
  gl_Position.xyz = vertexPosition_modelspace;
  gl_Position.w = 1.0;
}
""", """#version 410 core
out vec3 color;
void main(){
  color = vec3(1,0,0);
}
"""
        )
        game.open()
        //val game = GameBis()
    }
}