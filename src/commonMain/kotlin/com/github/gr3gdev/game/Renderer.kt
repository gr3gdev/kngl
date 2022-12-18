package com.github.gr3gdev.game

import com.github.gr3gdev.game.rendered.RenderObject
import glew.GLuint

open class Renderer {

    private val objects = arrayListOf<RenderObject>()

    fun add(obj: RenderObject) {
        objects.add(obj)
    }

    fun init(pid: GLuint) {
        objects.forEach {
            it.create(pid)
        }
    }

    fun render() {
        objects.forEach {
            it.render()
        }
    }

    fun dispose() {
        objects.forEach {
            it.dispose()
        }
    }
}