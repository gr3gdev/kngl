package com.github.gr3gdev.game.rendered

import com.github.gr3gdev.game.bean.Matrix4
import com.github.gr3gdev.game.bean.Vector3
import glew.*
import kotlinx.cinterop.*
import kotlin.math.PI

fun String.toGLcharVar(): CPointer<GLcharVar> = memScoped {
    return cstr.ptr
}

fun Float.toRadians(): Float {
    return this * PI.toFloat() / 180
}

abstract class Renderable(
    private val vertexAttributes: FloatArray,
    private val indices: IntArray
) {

    private var vertexAttributeIndex: UInt = 0U
    private var colorAttributeIndex: UInt = 1U
    private var textureCoordAttributeIndex: UInt = 2U

    private var matricesAttributeIndex: Int = 0
    private var matrices = floatArrayOf(
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f
    )

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
        matricesAttributeIndex = glGetAttribLocation!!(pid, "MVP".toGLcharVar())

        val projection = Matrix4.projection(45f.toRadians(), 4f / 3f, 0.1f, 100f)
        val view = Matrix4.lookAt(
            Vector3(4f, 3f, 3f),
            Vector3(0f, 0f, 0f),
            Vector3(0f, 1f, 0f)
        )
        val model = Matrix4.identity
        val mvp = projection * view * model
        matrices = mvp.values

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

        // TODO Load textures

        // texture coord attribute
        //val textureStartPosition: Long = 7 * sizeOf<FloatVar>()
        //enableVertexAttribArray(textureCoordAttributeIndex, 2, textureStartPosition)

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
        matrices.usePinned {
            glUniformMatrix4fv!!(
                matricesAttributeIndex,
                1,
                false.toByte().toUByte(),
                it.addressOf(0)
            )
        }
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