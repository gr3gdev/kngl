package com.github.gr3gdev.game

import com.github.gr3gdev.window.Shaders
import com.github.gr3gdev.window.Window
import glew.*
import kotlinx.cinterop.invoke

const val DEFAULT_VERTEX_SHARED = """
#version 430

in vec3 VertexPosition;
in vec3 VertexColor;

out vec3 VFragColor;

uniform mat4 MVP;

void main()
{
VFragColor= VertexColor;
vec4 v= vec4(VertexPosition, 1);
gl_Position= MVP * v;
}
"""

const val DEFAULT_FRAGMENT_SHARED = """
#version 430

in vec3 VFragColor;

out vec4 FragColor;

void main()
{
FragColor = vec4(VFragColor, 1.0);
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