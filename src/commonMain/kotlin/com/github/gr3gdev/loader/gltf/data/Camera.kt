package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    val orthographic: Orthographic ? = null,
    val perspective: Perspective ? = null,
    val type: String,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
) {
    @Serializable
    data class Orthographic(
        val xmag: Float,
        val ymag: Float,
        val zfar: Float,
        val znear: Float,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )
    @Serializable
    data class Perspective(
        val aspectRatio: Float ? = null,
        val yfov: Float,
        val zfar: Float ? = null,
        val znear: Float,
        val extensions: Extension ? = null,
        val extras: Extras? = null
    )
}
