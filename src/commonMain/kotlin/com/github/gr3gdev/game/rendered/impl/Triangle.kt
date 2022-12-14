package com.github.gr3gdev.game.rendered.impl

import com.github.gr3gdev.game.rendered.Renderable

class Triangle : Renderable(
    floatArrayOf(
        -1f, -1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
        1f, -1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
        0f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f
    ),
    intArrayOf(
        0, 1, 2
    )
)
