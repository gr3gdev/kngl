package com.github.gr3gdev.window

import glfw.*

abstract class Window(private val title: String, internal val width: Int, internal val height: Int) {

    private var forceStopped = false

    init {
        if (glfwInit() == GLFW_FALSE) {
            throw Error("Failed to initialize GLFW")
        }
        glfwWindowHint(GLFW_SAMPLES, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    }

    fun close() {
        forceStopped = true
    }

    abstract fun initRender()

    abstract fun renderLoop()

    abstract fun dispose()

    fun open() {
        val window = glfwCreateWindow(width, height, title, null, null)
            ?: throw Error("Failed to open GLFW window. If you have an Intel GPU, they are not 3.3 compatible.")
        glfwMakeContextCurrent(window)
        glfwSetInputMode(window, GLFW_STICKY_KEYS, GLFW_TRUE)
        initRender()
        while (glfwGetKey(
                window,
                GLFW_KEY_ESCAPE
            ) != GLFW_PRESS && glfwWindowShouldClose(window) == 0 && !forceStopped
        ) {
            renderLoop()
            glfwSwapBuffers(window)
            glfwPollEvents()
        }
        dispose()
        glfwTerminate()
    }

}