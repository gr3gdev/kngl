package com.github.gr3gdev.window

import glew.*
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCStringArray

object Shaders {
    fun compileShaderProgram(vertexShader: String, fragmentShader: String): GLuint = memScoped {
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
}