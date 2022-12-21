package com.github.gr3gdev.game.rendered

import glew.*
import kotlinx.cinterop.*

fun String.toGLcharVar(): CPointer<GLcharVar> = memScoped {
    return cstr.ptr
}

abstract class Renderable(
    private val vertexAttributes: FloatArray,
    private val indices: IntArray
) {

    private var vertexAttributeIndex: UInt = 0U
    private var colorAttributeIndex: UInt = 1U
    private var textureCoordAttributeIndex: UInt = 2U

    private var vao: UInt = 0U
    private var vbo: UInt = 0U
    private var ibo: UInt = 0U

    private fun genVertexArrays(): UInt {
        return memScoped {
            val output = alloc<UIntVar>()
            glGenVertexArrays!!(1, output.ptr)
            output.value
        }
    }

    private fun genBuffers(): UInt {
        return memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
    }

    fun create(pid: UInt) {
        //vertexAttributeIndex = glGetAttribLocation!!(pid, "VertexPosition")
        //colorAttributeIndex = glGetAttribLocation!!(pid, "VertexColor")

        vao = genVertexArrays()
        vbo = genBuffers()
        ibo = genBuffers()

        glBindVertexArray!!(vao)

        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)
        vertexAttributes.usePinned {
            glBufferData!!(
                GL_ARRAY_BUFFER.toUInt(),
                vertexAttributes.size.toLong(),
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }

        glBindBuffer!!(GL_ELEMENT_ARRAY_BUFFER.toUInt(), ibo)
        indices.usePinned {
            glBufferData!!(
                GL_ELEMENT_ARRAY_BUFFER.toUInt(),
                indices.size.toLong(),
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }

        // position attribute
        enableVertexAttribArray(vertexAttributeIndex, 3, 0L)

        // color attribute
        val colorStartPosition: Long = 3 * sizeOf<FloatVar>()
        enableVertexAttribArray(colorAttributeIndex, 4, colorStartPosition)

        // texture coord attribute
        val textureStartPosition: Long = 7 * sizeOf<FloatVar>()
        enableVertexAttribArray(textureCoordAttributeIndex, 2, textureStartPosition)

        // TODO Load textures
        /*
        glUseProgram!!(pid)
        glUniform1i!!(glGetUniformLocation!!(pid, "".toGLcharVar()), 0)
        glUseProgram!!(0U)
         */
    }

    private fun enableVertexAttribArray(index: UInt, size: Int, startPosition: Long) {
        glVertexAttribPointer!!(
            index,
            size,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            (9 * sizeOf<FloatVar>()).toInt(),
            startPosition.toCPointer()
        )
        glEnableVertexAttribArray!!(index)
    }

    fun render() {
        // glUniformMatrix4fv(matrixID, 1, false, mvp.values)

        glBindVertexArray!!(vao)
        glDrawElements(
            GL_TRIANGLES.toUInt(),
            indices.size,
            GL_UNSIGNED_INT.toUInt(),
            null
        )
    }

    fun dispose() {
        // TODO
    }
}