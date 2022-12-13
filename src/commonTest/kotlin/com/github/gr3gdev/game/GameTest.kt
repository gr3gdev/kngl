package com.github.gr3gdev.game

import kotlin.test.Test

class GameTest {

    @Test
    fun testTriangle() {
        val game = Game()
        game.start(
            """#version 410 core
layout(location = 0) in vec3 vertexPosition_modelspace;
void main() {
  gl_Position.xyz = vertexPosition_modelspace;
  gl_Position.w = 1.0;
}
""".trimIndent(), """#version 410 core
out vec3 color;
void main(){
  color = vec3(1,0,0);
}
""".trimIndent()
        )
        game.stop()
    }
}