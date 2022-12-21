package com.github.gr3gdev.game

import com.github.gr3gdev.window.Shaders
import com.github.gr3gdev.window.Window
import glew.*
import kotlinx.cinterop.invoke

const val DEFAULT_VERTEX_SHARED = """
attribute vec4 a_position;
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

const val DEFAULT_FRAGMENT_SHARED = """
#ifdef GL_ES
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
    #gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    gl_FragColor = vec3(1,0,0);
}
"""

class Game(
    title: String,
    private val renderer: Renderer,
    private val vertexShader: String = DEFAULT_VERTEX_SHARED,
    private val fragmentShader: String = DEFAULT_FRAGMENT_SHARED
) :
    Window(title, 400, 300) {

    private var pid: UInt = 0U

    override fun initRender() {
        if (glewInit() != 0u) {
            throw Error("Failed to initialize GLEW")
        }
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)
        glDisable(GL_CULL_FACE)
        glCullFace(GL_BACK)
        glViewport(0, 0, width, height)
        pid = Shaders.compileShaderProgram(vertexShader, fragmentShader)
        renderer.init(pid)
    }

    override fun renderLoop() {
        glClear(GL_COLOR_BUFFER_BIT.toUInt() or GL_DEPTH_BUFFER_BIT.toUInt())
        glClearColor(0f, 0f, 0f, 0f)
        glUseProgram!!(pid)
        renderer.render()
    }

    override fun dispose() {
        renderer.dispose()
    }

}