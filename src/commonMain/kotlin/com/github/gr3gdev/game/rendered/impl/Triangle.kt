package com.github.gr3gdev.game.rendered.impl

import com.github.gr3gdev.game.rendered.RenderObject

class Triangle : RenderObject(
    floatArrayOf(
        -0.8f, -0.8f, 0.0f,
        0.8f, -0.8f, 0.0f,
        0.0f, 0.8f, 0.0f
    )
)
