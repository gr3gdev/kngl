package com.github.gr3gdev.game.rendered

import glew.*
import kotlinx.cinterop.*

fun String.toGLcharVar(): CPointer<GLcharVar> = memScoped {
    return cstr.ptr
}

open class RenderObject(private val vertexBufferData: FloatArray) {

    private var vboId: UInt = 0U

    private fun loadDataInBuffer() {
        vboId = memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vboId)
        vertexBufferData.usePinned {
            glBufferData!!(
                GL_ARRAY_BUFFER.toUInt(),
                vertexBufferData.size.toLong() * 4,
                it.addressOf(0),
                GL_STATIC_DRAW.toUInt()
            )
        }
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), 0U)
    }

    fun create(pid: GLuint) {
        loadDataInBuffer()
        val vaoId = memScoped {
            val output = alloc<UIntVar>()
            glGenVertexArrays!!(1, output.ptr)
            output.value
        }
        glBindVertexArray!!(vaoId)
        val posAttributePosition = glGetAttribLocation!!(pid, "pos".toGLcharVar())
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vboId)
        glVertexAttribPointer!!(
            posAttributePosition.toUInt(),
            3,
            GL_FLOAT.toUInt(),
            false.toByte().toUByte(),
            0,
            0L.toCPointer()
        )
        glEnableVertexAttribArray!!(posAttributePosition.toUInt())
    }

    fun render() {
        glDrawArrays(GL_TRIANGLES.toUInt(), 0, 3)
    }

    fun dispose() {
        // TODO
    }
}