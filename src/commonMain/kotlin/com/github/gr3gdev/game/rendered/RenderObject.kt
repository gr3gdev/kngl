package com.github.gr3gdev.game.rendered

import glew.*
import kotlinx.cinterop.*

open class RenderObject(
    private val verticesPosition: FloatArray,
    private val indices: IntArray
) {

    private var vao: UInt = 0U
    private var vbo: UInt = 0U
    private var ibo: UInt = 0U

    private fun genVertexArrays() {
        // glGenVertexArrays(1, &VAO);
        vao = memScoped {
            val output = alloc<UIntVar>()
            glGenVertexArrays!!(1, output.ptr)
            output.value
        }
        // glBindVertexArray(VAO);
        glBindVertexArray!!(vao)
    }

    private fun initVBO() {
        // glGenBuffers(1, &vbo);
        vbo = memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
        // glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)
        // glBufferData(GL_ARRAY_BUFFER, sizeof(data), data, GL_STATIC_DRAW);
        verticesPosition.usePinned {
            glBufferData!!(
                GL_ARRAY_BUFFER.toUInt(),
                verticesPosition.size.toLong(),
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }
    }

    private fun initIBO() {
        // glGenBuffers(1, &ibo);
        ibo = memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
        // glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), ibo)
        // glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indexes), indexes, GL_STATIC_DRAW);
        indices.usePinned {
            glBufferData!!(
                GL_ARRAY_BUFFER.toUInt(),
                indices.size.toLong(),
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }
    }

    fun create() {
        genVertexArrays()
        initVBO()
        initIBO()

        // glEnableVertexAttribArray( 0 );
        glEnableVertexAttribArray!!(0U)
        // glVertexAttribPointer( 0, 2, GL_FLOAT, GL_FALSE, 0, 0 );
        glVertexAttribPointer!!(
            0U,
            3,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            0,
            0L.toCPointer()
        )
        // glBindVertexArray( 0 );
        glBindVertexArray!!(0U)
    }

    fun render() {
        // glBindVertexArray( VAO );
        glBindVertexArray!!(vao)
        // glDrawElements( GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0 );
        glDrawElements(
            GL_TRIANGLES.toUInt(),
            3,
            GL_UNSIGNED_INT.toUInt(),
            cValuesOf(0L.toCPointer())
        )
        // glBindVertexArray( 0 );
        glBindVertexArray!!(0U)
    }

    fun dispose() {
        // TODO
    }
}