package com.github.gr3gdev.loader.gltf.objects

import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val camera: Int ? = null,
    val children: Array<Int> ? = null,
    val skin: Int ? = null,
    val matrix: Array<Float> = arrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f),
    val mesh: Int ? = null,
    val rotation: Array<Float> = arrayOf(0f, 0f, 0f, 1f),
    val scale: Array<Float> = arrayOf(1f, 1f, 1f),
    val translation: Array<Float> = arrayOf(0f, 0f, 0f),
    val weights: Array<Float> ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)
