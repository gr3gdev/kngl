package com.github.gr3gdev

import com.github.gr3gdev.game.Game
import kotlinx.cinterop.*
import platform.glew.GLcharVar

fun String.toGLcharVar(): CPointer<CPointerVar<GLcharVar>> = memScoped {
    return cValuesOf(cstr.getPointer(memScope)).ptr
}

fun main() {
    Game().start()
}