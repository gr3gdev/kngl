package com.github.gr3gdev.game

import com.github.gr3gdev.game.rendered.Renderable

open class Renderer {

    private val renderables = arrayListOf<Renderable>()

    fun add(obj: Renderable) {
        renderables.add(obj)
    }

    fun init(pid: UInt) {
        renderables.forEach {
            it.create(pid)
        }
    }

    fun render() {
        renderables.forEach {
            it.render()
        }
    }

    fun dispose() {
        renderables.forEach {
            it.dispose()
        }
    }
}