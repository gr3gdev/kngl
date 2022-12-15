package com.github.gr3gdev.game

import com.github.gr3gdev.window.Shaders
import com.github.gr3gdev.window.Window
import glew.*
import kotlinx.cinterop.*

fun String.toGLcharVar(): CPointer<CPointerVar<GLcharVar>> = memScoped {
    return cValuesOf(cstr.getPointer(memScope)).ptr
}

class Game(private val title: String) {

    private var window: Window = Window(title, 400, 300)

    fun start() {
        this.start(
            """attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

void main()
{
    v_color = a_color;
    v_color.a = v_color.a * (256.0/255.0);
    v_texCoords = a_texCoord + 0;
    gl_Position =  u_projTrans * a_position;
}
""".trimIndent(), """#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

void main()
{
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}
""".trimIndent()
        )
    }

    fun start(vertexShader: String, fragmentShader: String) {
        window.init {
            if (glewInit() != 0u) {
                throw Error("Failed to initialize GLEW")
            }
            glViewport(0, 0, window.width, window.height)
            val vbo = initBuffer()
            initVertexArray()
            val pid = Shaders.compileShaderProgram(vertexShader, fragmentShader)

            window.render {
                glClear(glew.GL_COLOR_BUFFER_BIT.toUInt() or glew.GL_DEPTH_BUFFER_BIT.toUInt())
                glClearColor(0f, 0f, 0f, 1f)

                glUseProgram!!(pid)

                glEnableVertexAttribArray!!(0U)
                glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)
                glVertexAttribPointer!!(
                    0U,
                    3,
                    glew.GL_FLOAT.toUInt(),
                    false.toByte().toUByte(),
                    0,
                    0L.toCPointer()
                )

                glDrawArrays(GL_TRIANGLES.toUInt(), 0, 3)
                glDisableVertexAttribArray!!(0U)
            }
        }
        window.open()
    }

    private fun initBuffer(): UInt {
        val vbo = memScoped {
            val output = alloc<UIntVar>()
            glGenBuffers!!(1, output.ptr)
            output.value
        }
        glBindBuffer!!(GL_ARRAY_BUFFER.toUInt(), vbo)
        return vbo
    }

    private fun initVertexArray() {
        val vao = memScoped {
            val output = alloc<UIntVar>()
            glGenVertexArrays!!(1, output.ptr)
            output.value
        }
        glBindVertexArray!!(vao)
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
    }

    fun stop() {
        window.close()
    }
}