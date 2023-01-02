package com.github.gr3gdev.game

import com.github.gr3gdev.window.Shaders
import com.github.gr3gdev.window.Window
import glew.*
import kotlinx.cinterop.invoke

const val VERTEX_SHADER = """
#version 330 core
layout(location = 0) in vec3 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_texCoord;

uniform mat4 MVP;

out vec4 fragmentColor;

void main()
{
    gl_Position = MVP * vec4(a_position, 1);
    fragmentColor = a_color;
    fragmentColor.a = v_color.a * (256.0/255.0);
}
"""

const val FRAGMENT_SHADER = """
#version 330 core
in vec4 fragmentColor;

out vec4 gl_color;

void main()
{
    #gl_color = fragmentColor;
    gl_color = vec3(1,0,0);
}
"""

class Game(
    title: String,
    private val renderer: Renderer
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
        pid = Shaders.compileShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER)
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
        glDeleteProgram!!(pid)
    }

}