package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class BufferView(
    val buffer: Int,
    val byteOffset: Int = 0,
    val byteLength: Int,
    val byteStride: Int ? = null,
    val target: Int ? = null,
    val name: String ? = null,
    val extensions: Extension ? = null,
    val extras: Extras? = null
)
