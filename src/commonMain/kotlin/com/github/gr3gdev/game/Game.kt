package com.github.gr3gdev.game

import com.github.gr3gdev.window.Shaders
import com.github.gr3gdev.window.Window
import glew.*
import kotlinx.cinterop.invoke

const val DEFAULT_VERTEX_SHARED = """attribute vec4 a_position;
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
"""

const val DEFAULT_FRAGMENT_SHARED = """#ifdef GL_ES
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
"""

class Game(title: String, private val renderer: Renderer) {

    private var window: Window = Window(title, 400, 300)

    fun start() {
        this.start(DEFAULT_VERTEX_SHARED, DEFAULT_FRAGMENT_SHARED)
    }

    fun start(vertexShader: String, fragmentShader: String) {
        window.init {
            if (glewInit() != 0u) {
                throw Error("Failed to initialize GLEW")
            }
            glViewport(0, 0, window.width, window.height)
            val pid = Shaders.compileShaderProgram(vertexShader, fragmentShader)
            renderer.init(pid)

            window.render {
                glClear(GL_COLOR_BUFFER_BIT.toUInt() or GL_DEPTH_BUFFER_BIT.toUInt())
                glClearColor(0f, 0f, 0f, 0f)
                glUseProgram!!(pid)
                renderer.render()
            }
            renderer.dispose()
        }
        window.open()
    }

    fun stop() {
        window.close()
    }
}