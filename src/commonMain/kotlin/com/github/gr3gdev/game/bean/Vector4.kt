package com.github.gr3gdev.game.bean

class Vector4(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f, val w: Float = 0f) {

    constructor(vector: Vector3, w: Float): this(vector.x, vector.y, vector.z, w)
    constructor(vector: Vector3) : this(vector.x, vector.y, vector.z, 0f)

    fun dot(vector: Vector4): Float = x * vector.x + y * vector.y + z * vector.z + w * vector.w

}