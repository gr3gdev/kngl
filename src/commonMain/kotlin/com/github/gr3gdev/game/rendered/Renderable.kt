package com.github.gr3gdev.game.rendered

import glew.*
import kotlinx.cinterop.*

abstract class Renderable(
    private val vertexAttributes: FloatArray,
    private val indices: IntArray
) {

    private var vertexAttributeIndex: UInt = 0U
    private var colorAttributeIndex: UInt = 1U

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
        glVertexAttribPointer!!(
            vertexAttributeIndex,
            3,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            (8 * sizeOf<FloatVar>()).toInt(),
            0L.toCPointer()
        )
        glEnableVertexAttribArray!!(vertexAttributeIndex)

        // color attribute
        val colorStartPosition: Long = 6 * sizeOf<FloatVar>()
        glVertexAttribPointer!!(
            colorAttributeIndex,
            3,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            (8 * sizeOf<FloatVar>()).toInt(),
            colorStartPosition.toCPointer<COpaque>()
        )
        glEnableVertexAttribArray!!(colorAttributeIndex)

        // texture coord attribute
        /*
        val textureStartPosition: Long = 6 * FloatVar.size
        glVertexAttribPointer!!(
            textureCoordAttributeIndex,
            2,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            (8 * sizeOf<FloatVar>()).toInt(),
            textureStartPosition.toCPointer<COpaque>()
        )
        glEnableVertexAttribArray!!(textureCoordAttributeIndex)
         */
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