package com.github.gr3gdev.game

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import platform.glew.GL_RGB
import platform.glew.GL_UNSIGNED_BYTE
import platform.glew.glReadPixels
import kotlin.test.Test

class GameTest {

    private fun screenshot() = memScoped {
        val width = 400
        val height = 300
        val pixels = ByteArray(3 * width * height)
        glReadPixels(0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, pixels.toCValues())
        // TODO PNG.writeImage(imageData, )
    }

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