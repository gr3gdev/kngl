package com.github.gr3gdev.game.bean

class Vector3(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f) {

    fun normalized(): Vector3 {
        val lengthSquared: Float = x * x + y * y + z * z
        val l = 1f / lengthSquared
        return Vector3(x * l, y * l, z * l)
    }

    fun cross(vector: Vector3): Vector3 = Vector3(
        y * vector.z - z * vector.y,
        z * vector.x - x * vector.z,
        x * vector.y - y * vector.x
    )

    fun dot(vector: Vector3): Float = x * vector.x + y * vector.y * z * vector.z

    operator fun minus(vector: Vector3): Vector3 = Vector3(x - vector.x, y - vector.y, z - vector.z)

    operator fun div(value: Float): Vector3 = Vector3(x / value, y / value, z / value)

    operator fun times(vector: Vector3): Vector3 = Vector3(x * vector.x, y * vector.y, z * vector.z)

}