package com.github.gr3gdev.game.rendered.impl

import com.github.gr3gdev.game.rendered.RenderObject

class Cube : RenderObject(
    floatArrayOf(
        1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,    //0
        -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,   //1
        -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f,  //2
        1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f,   //3
        1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 0.0f,   //4
        -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f,  //5
        -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, //6
        1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f   //7
    ),
    intArrayOf(
        0, 1, 3, //top 1
        3, 1, 2, //top 2
        2, 6, 7, //front 1
        7, 3, 2, //front 2
        7, 6, 5, //bottom 1
        5, 4, 7, //bottom 2
        5, 1, 4, //back 1
        4, 1, 0, //back 2
        4, 3, 7, //right 1
        3, 4, 0, //right 2
        5, 6, 2, //left 1
        5, 1, 2  //left 2
    )
)
