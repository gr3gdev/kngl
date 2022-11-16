package com.github.gr3gdev.game

import kotlinx.cinterop.*
import platform.glew.*
import platform.glfw.*

class Game {

    init {
        if (glfwInit() == GLFW_FALSE) {
            throw Error("Failed to initialize GLFW")
        }
        glfwWindowHint(GL_SAMPLES, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    }

    private fun compileShaderProgram(vertexShader: String, fragmentShader: String): GLuint = memScoped {
        val vsId = glCreateShader!!(GL_VERTEX_SHADER.toUInt())
        val fsId = glCreateShader!!(GL_FRAGMENT_SHADER.toUInt())

        glShaderSource!!(vsId, 1, arrayOf(vertexShader).toCStringArray(memScope), null)
        glCompileShader!!(vsId)

        glShaderSource!!(fsId, 1, arrayOf(fragmentShader).toCStringArray(memScope), null)
        glCompileShader!!(fsId)

        val pId = glCreateProgram!!()

        glAttachShader!!(pId, vsId)
        glAttachShader!!(pId, fsId)
        glLinkProgram!!(pId)

        glDetachShader!!(pId, vsId)
        glDetachShader!!(pId, fsId)

        glDeleteShader!!(vsId)
        glDeleteShader!!(fsId)

        return pId
    }

    fun start() {
        val width = 400
        val height = 300

        val window = glfwCreateWindow(width, height, "Hello world", null, null)
            ?: throw Error("Failed to open GLFW window. If you have an Intel GPU, they are not 3.3 compatible.")
        glfwMakeContextCurrent(window)
        glfwSetInputMode(window, GLFW_STICKY_KEYS, GLFW_TRUE)

        if (glewInit() != 0u) {
            throw Error("Failed to initialize GLEW")
        }

        glViewport(0, 0, width, height)

        val vao = memScoped {
            val output = alloc<UIntVar>()
            glGenVertexArrays!!(1, output.ptr)
            output.value
        }
        glBindVertexArray!!(vao)

        val vbo = memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)

        val vertexArray = floatArrayOf(
            -0.8f, -0.8f, 0.0f,
            0.8f, -0.8f, 0.0f,
            0.0f, 0.8f, 0.0f
        )

        vertexArray.usePinned {
            glBufferData!!(
                GL_ARRAY_BUFFER.toUInt(),
                vertexArray.size.toLong() * 4,
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }

        val pId = compileShaderProgram(
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

        while (glfwGetKey(window, GLFW_KEY_ESCAPE) != GLFW_PRESS && glfwWindowShouldClose(window) == 0) {
            glClear(GL_COLOR_BUFFER_BIT.toUInt() or GL_DEPTH_BUFFER_BIT.toUInt())
            glClearColor(0f, 0f, 0f, 1f)

            glUseProgram!!(pId)

            glEnableVertexAttribArray!!(0U)
            glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)
            glVertexAttribPointer!!(
                0U,
                3,
                GL_FLOAT.toUInt(),
                false.toByte().toUByte(),
                0,
                0L.toCPointer()
            )

            glDrawArrays(GL_TRIANGLES.toUInt(), 0, 3)
            glDisableVertexAttribArray!!(0U)

            glfwSwapBuffers(window)
            glfwPollEvents()
        }

        glfwTerminate()
    }

}