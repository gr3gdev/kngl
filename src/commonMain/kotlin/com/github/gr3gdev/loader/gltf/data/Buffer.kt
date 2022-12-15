package com.github.gr3gdev.loader.gltf.data

import kotlinx.serialization.Serializable

@Serializable
data class Buffer(
    val uri: String? = null,
    val byteLength: Int,
    val name: String? = null,
    val extensions: Extension? = null,
    val extras: Extras? = null
)
